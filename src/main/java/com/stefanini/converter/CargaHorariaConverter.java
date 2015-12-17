package com.stefanini.converter;

import java.text.DecimalFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="cargaHorariaConverter") 
public class CargaHorariaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		if (value != null && !value.equals("")) {
			String cargaHoraria = value.replaceAll("\\,", "\\.");
			try {
				double cargaHorariaFormatada = Double.parseDouble(cargaHoraria);
				return cargaHorariaFormatada;
			} catch (NumberFormatException e) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erro de conversão de Carga Horária",
						"O valor informado não é um número de Carga Horária!");
				throw new ConverterException(message);
			}
		} else {			
			return null;
		}
	}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	
    	DecimalFormat df = new DecimalFormat("#.00");
    	double numero = Double.parseDouble(value.toString());
    	String numeroFormatado = df.format(numero);
 	
    	
        if (numeroFormatado != null && numeroFormatado.length() == 4){
        	
        	numeroFormatado = "0"+numeroFormatado;
        	return numeroFormatado;
        }
        
        return numeroFormatado;
    }

}
