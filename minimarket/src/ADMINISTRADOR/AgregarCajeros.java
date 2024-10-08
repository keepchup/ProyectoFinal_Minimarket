package ADMINISTRADOR;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la interfaz para agregar cajeros en el minimarket.
 */
public class AgregarCajeros {
    public JPanel ACajeros;
    private JButton agregarCajeroButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton menuPrincipalButton;

    /**
     * Constructor de la clase AgregarCajeros que inicializa los componentes y establece las acciones de los botones.
     */
    public AgregarCajeros() {
        menuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(ACajeros);
                if (currentFrame != null) {
                    currentFrame.setVisible(false);
                }

                JFrame MenuAdminFrame = new JFrame("Agregar Cajeros");
                MenuAdminFrame.setContentPane(new menuAdmin().menuAdmin);
                MenuAdminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                MenuAdminFrame.pack();
                MenuAdminFrame.setSize(700, 500);
                MenuAdminFrame.setLocationRelativeTo(null);
                MenuAdminFrame.setVisible(true);
            }
        });

        agregarCajeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = textField2.getText();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/")) {
                    MongoDatabase database = mongoClient.getDatabase("proyecto_minimarket");
                    MongoCollection<Document> collection = database.getCollection("cajeros");

                    Document query = new Document("user", username);
                    if (collection.find(query).first() != null) {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe.");
                        return;
                    }

                    Document document = new Document("user", username)
                            .append("password", password);
                    collection.insertOne(document);

                    JOptionPane.showMessageDialog(null, "Cajero agregado exitosamente.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al agregar el cajero.");
                }
            }
        });
    }
}
