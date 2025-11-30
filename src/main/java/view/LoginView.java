package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import view.MainMenuFrame.BackgroundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class LoginView extends BackgroundPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(10);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(10);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton signUp;
    private final JButton cancel;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {

        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);
        usernameErrorField.setFont(new Font("Arial", Font.PLAIN, 28));
        usernameErrorField.setForeground(new Color(255,0,255));
        usernameErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordErrorField.setFont(new Font("Arial", Font.PLAIN, 28));
        passwordErrorField.setForeground(new Color(255,0,255));
        passwordErrorField.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon image1 = new ImageIcon(LoginView.class.getResource("/WideLogo.png"));
        Image scaled = image1.getImage().getScaledInstance(-1, 300, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaled);
        final JLabel image1label = new JLabel(image1);
        image1label.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel usernameText = new JLabel("Username:");
        usernameText.setFont(new Font("Arial", Font.PLAIN, 36));
        usernameText.setForeground(new Color(255,0,255));
        usernameInputField.setFont(new Font("Arial", Font.PLAIN, 36));
        final LabelTextPanel usernameInfo = new LabelTextPanel(usernameText, usernameInputField);
        usernameInfo.setMaximumSize(new Dimension(20000,100));
        usernameInfo.setOpaque(false);

        final JLabel passwordText = new JLabel("Password:");
        passwordText.setFont(new Font("Arial", Font.PLAIN, 36));
        passwordText.setForeground(new Color(255,0,255));
        passwordInputField.setFont(new Font("Arial", Font.PLAIN, 36));
        final LabelTextPanel passwordInfo = new LabelTextPanel(passwordText, passwordInputField);
        passwordInfo.setMaximumSize(new Dimension(20000,100));
        passwordInfo.setOpaque(false);

        logIn = new JButton("Log In");
        logIn.setFont(new Font("Arial", Font.PLAIN,48));
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN,21));
        cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel.setBackground(new Color(255,0,0));
        signUp = new JButton("Don't Have an Account? - Sign Up Here");
        signUp.setFont(new Font("Arial", Font.PLAIN,21));
        signUp.setAlignmentX(Component.CENTER_ALIGNMENT);


        logIn.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            final LoginState currentState = loginViewModel.getState();
                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );



        cancel.addActionListener(this);

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.switchToSignupView();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameInputField.setText("");
                passwordInputField.setText("");

                usernameErrorField.setText("");
                passwordErrorField.setText("");

                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername("");
                currentState.setPassword("");
                currentState.setLoginError("");
                loginViewModel.setState(currentState);
            }
        });

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(image1label);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(passwordInfo);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(logIn);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(cancel);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(signUp);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
    }


    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());


    }


    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}

