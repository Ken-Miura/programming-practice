/* Copyright (C) 2016 Ken Miura */
package ch24.ex24_02;

import java.util.Currency;
import java.util.Locale;

/**
 * @author Ken Miura
 *
 */
public final class CurrencySymbol {

	private static final Locale[] LOCALES = {
		Locale.JAPAN,
		Locale.US,
		Locale.CANADA,
		Locale.ITALY,
		Locale.CHINA,
		Locale.FRANCE
	};
	
	public static void main(String[] args) {
		for (Locale locale: LOCALES) {
			System.out.println("--");
			displayCurrencySymbols(locale);
		}
	}

	private static void displayCurrencySymbols(Locale currencyLocale) {
		System.out.println("currency symbol of " + currencyLocale);
		Currency currency = Currency.getInstance(currencyLocale);
		for (final Locale locale: LOCALES) {
			System.out.println(getCurrencySymbol(currency, locale) + " in " + locale);
		}
	}
	
	private static String getCurrencySymbol (Currency currency, Locale viewLocale) {
		return currency.getSymbol(viewLocale);
	}
}
