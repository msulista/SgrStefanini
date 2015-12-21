package com.stefanini.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "valorFinalConverter")
public class ValorInicialConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		FacesContext fc = FacesContext.getCurrentInstance();
		Locale l = fc.getViewRoot().getLocale();

		if (value != null && !value.equals("0")) {
			value = value.trim();
			if (value.length() > 0) {
				try {
					return new BigDecimal(NumberFormat.getNumberInstance(l).parse(value).doubleValue());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}else{
			FacesMessage message = new FacesMessage("Valor Inicial é Obrigatório!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(message);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		if (value == null) {
			return "";
		}
		if (value instanceof String) {
			return (String) value;
		}
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			Locale l = fc.getViewRoot().getLocale();
			NumberFormat formatador = NumberFormat.getNumberInstance(l);
			formatador.setGroupingUsed(true);
			return formatador.format(value);

		} catch (Exception e) {
			throw new ConverterException("Formato não é número.");
		}

	}
}
