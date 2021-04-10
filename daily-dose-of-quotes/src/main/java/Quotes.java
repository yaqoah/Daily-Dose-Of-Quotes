import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Quotes {
	private Elements quotes;
	private Random randomPage;
	private Random randomQuote;
	private int pageNumber;
	private int quoteNumber;
	private Document document;
	private String url;
	private final String CLASS_NAME;
	private final int NUMBER_OF_PAGES;
	private final int QUOTES_PER_PAGE;

	public Quotes() {
		url = "https://www.less-real.com/?p=";

		randomPage = new Random();
		randomQuote = new Random();

		CLASS_NAME = "quoteText";
		NUMBER_OF_PAGES = 431;
		QUOTES_PER_PAGE = 20;
	}

	public void processUrl() {
		try {
			document = Jsoup.connect(url + pageNumber).get();

		} catch (IOException ex) {
			System.err.println("HTML not extracted");
		}
	}

	public void selectQuotes() {
		quotes = document.getElementsByClass(CLASS_NAME);
	}

	public String retrieve() {
		pageNumber = randomPage.nextInt(NUMBER_OF_PAGES);
		quoteNumber = randomQuote.nextInt(QUOTES_PER_PAGE);

		processUrl();
		selectQuotes();

		return quotes.get(quoteNumber).text();
	}

	public void openURL() {
		try {
			Desktop.getDesktop().browse(new URI(url + pageNumber));
		} catch (IOException e) {
			System.err.println("browser is not found -openURL-");
		} catch (URISyntaxException e) {
			System.err.println("permissions not given -openURL-");
		}
	}

}
