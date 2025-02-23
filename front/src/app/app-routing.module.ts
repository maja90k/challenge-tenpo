import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditComponent } from './components/edit/edit.component';
import { FormComponent } from './components/form/form.component';

const routes: Routes = [

  // Definicion de ruta del componente
  { path: '', component: FormComponent },
  { path: 'edit', component: EditComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
