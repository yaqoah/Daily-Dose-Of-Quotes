import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;


public class QuotesTray {
	private TrayIcon trayIcon;
	private SystemTray myTray;
	private Quotes animeRelated;
	private boolean active;
	private ArrayList<BufferedImage> icons;

	public QuotesTray() {
		animeRelated = new Quotes();
		active = true;
		icons = new ArrayList<BufferedImage>();
	}

	public void checkSupported() {
		if (SystemTray.isSupported()) {
			initializeIcons();
			assignIcon();
			assignMenu();
			create();
		}
	}

	public void initializeIcons() {
		BufferedImage killuaGon = null;
		BufferedImage narutoSasuka = null;
		BufferedImage hinata = null;

		try {
			killuaGon = ImageIO
					.read(getClass()
					.getResource("Resources/KilluaAndGon.jpg"));
			narutoSasuka = ImageIO
					.read(getClass()
					.getResource("/Resources/NarutoSasuka.jpg"));
			hinata = ImageIO
					.read(getClass()
					.getResource("/Resources/Hinata.png"));
			
			
		} catch (IOException io) {
			System.err.println("error on reading images");
		} catch (IllegalArgumentException io) {
			System.err.println("image input is null");
		}
		addIcons(hinata, narutoSasuka, killuaGon);
	}

	public void addIcons(BufferedImage haikyuu, BufferedImage naruto, BufferedImage hunter) {
		icons.add(hunter);
		icons.add(naruto);
		icons.add(haikyuu);
	}

	public void assignIcon() {
		trayIcon = new TrayIcon(icons.get(0) ,"delivering wisdom");
		trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent c) {
				if(SwingUtilities.isLeftMouseButton(c)) {
					animeRelated.openURL();
				}
			}
		});
	}

	public void assignMenu() {
		PopupMenu popup = new PopupMenu();
		trayIcon.setPopupMenu(popup);
		MenuItem quitItem = new MenuItem("quit");
		popup.add(quitItem);
		quitItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				active = false;
				myTray.remove(trayIcon);
				Thread.currentThread().interrupt();
			}
		});
	}

	public void create() {
		myTray = SystemTray.getSystemTray();
		try {
			myTray.add(trayIcon);
		} catch (AWTException awt) {
			System.err.println("icon not added to tray");
		}
	}

	public void showMessage() {
		animate();
		trayIcon
		.displayMessage("~Anime Saying~",
				animeRelated.retrieve(),
				MessageType.INFO);
		
	}

	public void animate() {
		BufferedImage image = icons.get(0);
		trayIcon.setImage(image);
		trayIcon.setImageAutoSize(true);
		icons.remove(0);
		icons.add(image);
	}

	public boolean isActive() {
		return active;
	}

}
