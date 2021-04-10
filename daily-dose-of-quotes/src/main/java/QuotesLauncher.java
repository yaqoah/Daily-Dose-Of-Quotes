
public class QuotesLauncher {

	public static void main(String[] args) {
		QuotesTray animeNotification = new QuotesTray();

		animeNotification.checkSupported();

		while (animeNotification.isActive()) {

			animeNotification.showMessage();

			try {
				Thread.sleep(1000 * 15);
			} catch (InterruptedException ex) {
				System.out.println("user called quits");
			}

		}

	}

}