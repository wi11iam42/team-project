package view;

import interfaceadapter.login.LoginState;
import interfaceadapter.signup.SignupController;
import interfaceadapter.signup.SignupState;
import interfaceadapter.signup.SignupViewModel;
import view.MainMenuFrame.BackgroundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class SignupView extends BackgroundPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController = null;

    private final JButton signUp;
    private final JButton cancel;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon image1 = new ImageIcon(GameSelectView.class.getResource("/WideLogo.png"));
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

        final JLabel repeatPasswordText = new JLabel("Confirm Password:");
        repeatPasswordText.setFont(new Font("Arial", Font.PLAIN, 36));
        repeatPasswordText.setForeground(new Color(255,0,255));
        repeatPasswordInputField.setFont(new Font("Arial", Font.PLAIN, 36));
        final LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordText, repeatPasswordInputField);
        repeatPasswordInfo.setMaximumSize(new Dimension(20000,100));
        repeatPasswordInfo.setOpaque(false);

        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setFont(new Font("Arial", Font.PLAIN,48));
        signUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        toLogin = new JButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        toLogin.setFont(new Font("Arial", Font.PLAIN,21));
        toLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancel.setFont(new Font("Arial", Font.PLAIN,21));
        cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel.setBackground(new Color(255,0,0));

        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        signupController.switchToLoginView();
                    }
                }
        );

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameInputField.setText("");
                passwordInputField.setText("");
                repeatPasswordInputField.setText("");



                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername("");
                currentState.setPassword("");
                currentState.setRepeatPassword("");
                signupViewModel.setState(currentState);
            }
        });

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(image1label);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(signUp);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(cancel);
        this.add(Box.createRigidArea(new Dimension(0, 25)));
        this.add(toLogin);
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
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
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
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
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
