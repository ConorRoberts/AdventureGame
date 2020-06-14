package adventure;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class AdventureView extends JFrame{
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    private Game game;
    private Container contentPane;
    private TextArea gameOutput;
    private TextField gameInput;

    public AdventureView(Game g){
        super();
        game = g;
        setUpSize();
        setMainContainer();

        JPanel panel = new JPanel();
        gameInput = new TextField("Welcome to Adventure!");
        gameOutput=new TextArea();
        contentPane.add(gameInput);
        contentPane.add(gameOutput);
        JButton actionButton = new JButton("Submit");
        actionButton.addActionListener(listen->gameAction(gameInput.getText()));
        contentPane.add(actionButton);

        JButton gameButton = new JButton("Get the game");
        gameButton.addActionListener(listen->startTheGame());
        contentPane.add(gameButton);

        pack();
    }

    private void startTheGame(){
        //set game.adventure
        InputStream inputStream=Game.class.getClassLoader().getResourceAsStream("default.json");
        game.setAdventure(game.generateAdventure(game.loadAdventureJson(inputStream)));
        game.setParser(new Parser(game.getAdventure()));
        roomWelcome();
    }

    private void roomWelcome(){
        gameOutput.append("Room: "+game.getAdventure().getCurrentRoom().toString()+"\n");
        gameOutput.append(game.getAdventure().listRoomItems()+"\n");
    }

    private void setMainContainer(){
        contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
    }

    /*private JButton createButton(String text){
        JButton button = new JButton("Enter Command");
        button.setBackground(new Color(24, 105, 66));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
        String cmdText = gameInput.getText();
        button.addActionListener(listen->handleCommand(cmdText));
        return button;
    }*/

    private void gameAction(String str){
        try{
            Command command = game.getParser().parseUserCommand(str);
            gameOutput.append(game.getAdventure().executeCommand(command));
        }catch (InvalidCommandException e){
            gameOutput.append("Usage Error - Type 'help' for a list of commands.");
        }
        gameOutput.append("\n");
        roomWelcome();
    }

    private void setUpSize(){
        setSize(WIDTH, HEIGHT);
        setTitle("Adventure Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        Game g = new Game();
        AdventureView adv = new AdventureView(g);
        adv.setVisible(true);
    }
}