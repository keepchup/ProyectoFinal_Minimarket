import com.mongodb.client.*;
import org.bson.Document;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class LOGIN {
    public JPanel LOGIN;
    private JButton iniciarSesiónButton;
    private JPasswordField passwordText;
    private JTextField userText;
    private JComboBox modosBox;
    private JLabel img;

    public LOGIN() {
        try{
            URL url = new URL("https://static.vecteezy.com/system/resources/thumbnails/019/879/186/small/user-icon-on-transparent-background-free-png.png");
            ImageIcon icon = new ImageIcon(url);
            img.setIcon(icon);
        } catch (Exception e){
            e.printStackTrace();
        }

        iniciarSesiónButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (modosBox.getSelectedIndex()) {
                    case 1:
                        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/")) {
                            System.out.println("Conexion establecida");
                            MongoDatabase database = mongoClient.getDatabase("proyecto_minimarket");
                            MongoCollection collection = database.getCollection("administradores");
                            String username = userText.getText();
                            String password = passwordText.getText();
                            FindIterable<Document> documents = collection.find(new Document("user", userText.getText()).append("password", passwordText.getText()));

                            if (documents.first() != null) {
                                String user = (String) documents.first().get("user");
                                String pass = (String) documents.first().get("password");

                                if (user.equals(username) && pass.equals(password)) {
                                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(LOGIN);
                                    if (currentFrame != null) {
                                        currentFrame.setVisible(false);
                                    }

                                    JFrame menu = new JFrame();
                                    menu.setContentPane(new menuAdmin().menuAdmin);
                                    menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    menu.pack();
                                    menu.setSize(700, 500);
                                    menu.setLocationRelativeTo(null);
                                    menu.setVisible(true);
                                    new menuAdmin();

                                }
                            } else {
                                JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrecta");
                            }
                        }
                        break;

                    case 2:
                        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/")) {
                            System.out.println("Conexion establecida");
                            MongoDatabase database = mongoClient.getDatabase("proyecto_minimarket");
                            MongoCollection collection = database.getCollection("cajeros");
                            String username = userText.getText();
                            String password = passwordText.getText();
                            FindIterable<Document> documents = collection.find(new Document("user", userText.getText()).append("password", passwordText.getText()));

                            if (documents.first() != null) {
                                String user = (String) documents.first().get("user");
                                String pass = (String) documents.first().get("password");

                                if (user.equals(username) && pass.equals(password)) {
                                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(LOGIN);
                                    if (currentFrame != null) {
                                        currentFrame.setVisible(false);
                                    }

                                    JFrame menu = new JFrame();
                                    menu.setContentPane(new menuCajeros().menuCaja);
                                    menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    menu.pack();
                                    menu.setSize(700, 500);
                                    menu.setLocationRelativeTo(null);
                                    menu.setVisible(true);
                                    new menuAdmin();

                                }
                            } else {
                                JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrecta");
                            }
                        }
                        break;
                }
            }
        });
    }

    public Container LOGIN() {
        return LOGIN;
    }
}