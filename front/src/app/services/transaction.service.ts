import { Injectable } from '@angular/core';
import axios from 'axios'; // Importa Axios directamente
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private apiUrl = environment.apiUrl;

  constructor() { }

  async saveTransaction(transaction: any) {
    try {
      const res = await axios.post(`${this.apiUrl}/transaction`, transaction);
      alert('Transferencia realizada con éxito!');
      return res.data;
    } catch (error) {
      console.error('Error al guardar la transacción:', error);
      alert('Ocurrió un error al realizar la transferencia. Inténtalo de nuevo más tarde.');
      throw error;
    }
  }

  async updateTransaction(transaction: any) {
    try {
      const { amount, bankDraft, name } = transaction;
      const payload = { amount, bankDraft, name };

      const res = await axios.put(`${this.apiUrl}/transaction/${transaction.id}`, payload);
      alert('Transacción actualizada con éxito!');
      return res.data;
    } catch (error) {
      console.error('Error al actualizar la transacción:', error);
      alert('Ocurrió un error al actualizar la transacción. Inténtalo de nuevo más tarde.');
      throw error;
    }
  }

  async getAll() {
    const res = await axios.get(`${this.apiUrl}/transactions/paginated`);
    return res.data;
  }

  async deleteTransaction(id: number) {
    try {
      const res = await axios.delete(`${this.apiUrl}/transaction/${id}`);
      alert('Transferencia eliminada con éxito!');
      return res.data;
    } catch (error) {
      console.error('Error al eliminar la transferencia:', error);
      throw error;
    }
  }
}