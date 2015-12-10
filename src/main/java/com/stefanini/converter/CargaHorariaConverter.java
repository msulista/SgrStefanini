package com.stefanini.converter;

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
    	
    	String cargaHoraria = value.toString();
    	
    	
        if (cargaHoraria != null && cargaHoraria.length() == 5){
        	String cargaHorariaFormatada = cargaHoraria.replaceAll("\\.", "");
        	cargaHorariaFormatada = cargaHorariaFormatada.substring(0, 2) + "," + cargaHorariaFormatada.substring(2,4);
        	return cargaHorariaFormatada;
        }
        
        return null;
    }

}
