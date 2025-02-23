import { Component, OnInit } from '@angular/core';
import { TransactionService } from 'src/app/services/transaction.service';
import { DatePipe } from '@angular/common';
declare var $: any;

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
  providers: [DatePipe] 
})
export class FormComponent implements OnInit {

  transactions: any = [];
  selectedTransaction: any;
  maxDate: string;
  amountError: boolean = false;

  formTransaction: any = {
    amount: "",
    bankDraft: "",
    name: "",
    transactionDate: ""
  };

  editTransactionData: any = {
    amount: "",
    bankDraft: "",
    name: "",
    transactionDate: ""
  };

  constructor(private transactionService: TransactionService, private datePipe: DatePipe) {
    const today = new Date();
    this.maxDate = today.toISOString().split('T')[0]; 
  }

  ngOnInit(): void {
    this.getTransaction();
  }

  async onSubmit() {
    if (this.formTransaction.amount < 0) {
        this.amountError = true;
        return; 
    }

    if (!this.formTransaction.bankDraft || !this.formTransaction.name || !this.formTransaction.transactionDate) {
        alert('Todos los campos son obligatorios.');
        return; 
    }

    const today = new Date();
    const dd: string = String(today.getDate()).padStart(2, '0');
    const mm: string = String(today.getMonth() + 1).padStart(2, '0');
    const yyyy: number = today.getFullYear();
    const formattedDate: string = `${yyyy}-${mm}-${dd}`;

    this.formTransaction.transactionDate = formattedDate;

    const transactionDate = new Date(this.formTransaction.transactionDate);
    if (transactionDate > today) {
        alert('La fecha de la transacción no puede ser mayor que la fecha de hoy.');
        return;
    }

    try {
        await this.transactionService.saveTransaction(this.formTransaction);
        alert('Transacción guardada con éxito!');
        this.getTransaction();
        this.resetForm();
    } catch (error) {
        console.error('Error al guardar la transacción:', error);
        alert('Ocurrió un error al guardar la transacción. Inténtalo de nuevo más tarde.');
    }
}

  getTransaction() {
    this.transactionService
      .getAll()
      .then(transactions => {
        this.transactions = transactions.map((transaction: any) => {
          transaction.transactionDate = this.datePipe.transform(transaction.transactionDate, 'yyyy-MM-dd HH:mm:ss');
          return transaction;
        });
      })
      .catch(error => console.error('Error al obtener las transacciones:', error)); 
  }

  editTransaction(transaction: any) {
    this.editTransactionData = { ...transaction }; 
    $('#editTransactionModal').modal('show'); 
  }

  async onEditSubmit() {
    this.amountError = false;
    if (this.editTransactionData.amount < 0) {
        this.amountError = true;
        return; 
    }

    try {
        await this.transactionService.updateTransaction(this.editTransactionData); 
        alert('Transacción actualizada con éxito!');
        this.getTransaction();
        $('#editTransactionModal').modal('hide'); 
    } catch (error) {
        console.error('Error al actualizar la transacción:', error);
        alert('Ocurrió un error al actualizar la transacción. Inténtalo de nuevo más tarde.');
    }
}

  confirmDelete(transaction: any) {
    this.selectedTransaction = transaction;
    $('#confirmDeleteModal').modal('show'); 
  }

  deleteTransaction() {
    if (this.selectedTransaction) {
        this.transactionService.deleteTransaction(this.selectedTransaction.id)
            .then(() => {
                this.transactions = this.transactions.filter((t: any) => t.id !== this.selectedTransaction.id);
                this.selectedTransaction = null;
                $('#confirmDeleteModal').modal('hide');
            })
            .catch(error => {
                console.error('Error al eliminar la transacción:', error);
                alert('Ocurrió un error al eliminar la transacción. Inténtalo de nuevo más tarde.');
            });
    }
}

  resetForm() {
    this.formTransaction = {
      amount: "",
      bankDraft: "",
      name: "",
      transactionDate: ""
    };
  }
}