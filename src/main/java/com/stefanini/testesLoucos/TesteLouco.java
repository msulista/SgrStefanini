package com.stefanini.testesLoucos;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
        @URLMapping(id = "timer", pattern = "/testeLouco", viewId = "/pages/testeLouco/timerEduQuiz.xhtml")
       })
public class TesteLouco{
		
	private static int numero = 35;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void contador(){
		if(this.numero > 0){
			this.numero--;
			if(this.numero >= 10 && this.numero <= 11){
				FacesMessage message = new FacesMessage("Faltam só 10 segundos não vai dar tempo!!!", " O tempo acabou.");
	            FacesContext.getCurrentInstance().addMessage(null, message);		
			}
		}else{
			FacesMessage message = new FacesMessage("Acabou o tempo Magrão!!! Já éras!!!", " O tempo acabou.");
            FacesContext.getCurrentInstance().addMessage(null, message);		
		}
	}
}
