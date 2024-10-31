package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    public boolean logeado;
    private JLabel label; 
    private JButton botonLogIn; 
    public JButton btnSalir;
    public MenuPrincipal() {
        logeado = false;

        // Configuración de la ventana
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel superior
        JPanel barraAlta = new JPanel(new BorderLayout());
        barraAlta.setBackground(Color.RED);
        barraAlta.add(new JLabel("007Games", SwingConstants.CENTER));

        botonLogIn = new JButton("LogIn/Reg");
        barraAlta.add(botonLogIn, BorderLayout.EAST);

        JButton btnSalir = new JButton("Salir");
        barraAlta.add(btnSalir, BorderLayout.WEST);
        
        btnSalir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(logeado) {
					label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
		        	botonLogIn.setEnabled(true);
		        	logeado=false;
				}else {
					dispose();
				}
			}
        	
        });
        botonLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogIn login = new LogIn(MenuPrincipal.this); // Pasar la referencia de MenuPrincipal
                login.setVisible(true);
            }
        });

        // Panel central
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Texto de bienvenida
        label = new JLabel("Bienvenido al Menú Principal, ¿A qué desea jugar?", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(90, 20));
        panel.add(label, BorderLayout.NORTH);

        // Panel para los botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));

        // Botón Ruleta con imagen
        ImageIcon iconoRuleta = new ImageIcon(getClass().getResource("/img/Ruleta.png"));
        JButton botonRuleta = new JButton("RULETA", iconoRuleta);

        Image scaledImage = iconoRuleta.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        botonRuleta.setIcon(scaledIcon);
        botonRuleta.setPreferredSize(new Dimension(180, 60));
        botonRuleta.setHorizontalTextPosition(SwingConstants.CENTER);
        botonRuleta.setVerticalTextPosition(SwingConstants.CENTER);
        botonRuleta.setForeground(Color.BLACK);
        botonRuleta.setFont(new Font("Monospace", Font.BOLD, 30));
        buttonPane.add(botonRuleta);

        botonRuleta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(logeado) {
            		new VentanaRuleta();
            	}
            	else {
            		LogIn login = new LogIn(MenuPrincipal.this); 
                    login.setVisible(true);
            	}
            }
        });

        // Botón Slots con imagen
        ImageIcon iconoSlot = new ImageIcon(getClass().getResource("/img/Slot.png"));
        JButton botonSlot = new JButton("SLOTS", iconoSlot);

        Image scaledImagen = iconoSlot.getImage().getScaledInstance(180, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcono = new ImageIcon(scaledImagen);
        botonSlot.setIcon(scaledIcono);
        botonSlot.setPreferredSize(new Dimension(180, 60));
        botonSlot.setHorizontalTextPosition(SwingConstants.CENTER);
        botonSlot.setVerticalTextPosition(SwingConstants.CENTER);
        botonSlot.setForeground(Color.BLACK);
        botonSlot.setFont(new Font("Monospace", Font.BOLD, 30));
        buttonPane.add(botonSlot);

        botonSlot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(logeado) {
            		new VentanaSlot();
            	}
            	else {
            		LogIn login = new LogIn(MenuPrincipal.this); 
                    login.setVisible(true);
            	}
            }
        });

        panel.add(buttonPane, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
        add(barraAlta, BorderLayout.NORTH);

        setVisible(true);
    }

    
    public void actualizarEstado() {
        if (logeado) {
            label.setText("¡Bienvenido al Menú Principal! Ya estás logueado.");
            botonLogIn.setEnabled(false);
        }
        else {
        	label.setText("Bienvenido al Menú Principal, ¿A qué desea jugar?");
        	botonLogIn.setEnabled(true);
        }
    }
    


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
    
}
