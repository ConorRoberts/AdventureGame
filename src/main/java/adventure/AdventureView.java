package adventure;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.File;
import java.io.InputStream;

public class AdventureView extends JFrame{
    public static final int WIDTH = 600;
    public static final int HEIGHT = 700;
    public static final int OUTPUT_WIDTH=50;
    public static final int OUTPUT_HEIGHT=20;
    public static final int INVENTORY_WIDTH=40;
    public static final int INVENTORY_HEIGHT=20;
    private final Game game;
    private Container contentPane;
    private TextArea gameOutput;
    private TextField gameInput;
    private TextArea gameInventory;

    public AdventureView(Game g){
        super();
        game = g;
        setMainContainer();

        startTheGame();

        pack();
    }

    private TextField createInputArea(){
       return new TextField("");
    }

    private JButton createLoadButton(){
        JButton gameButton = new JButton("Load Adventure");
        gameButton.addActionListener(listen->loadAdventureButton());
        return gameButton;
    }

    private JButton createCommandButton(){
        JButton button = new JButton("Submit");
        button.setBackground(Color.BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(listen->gameAction(gameInput.getText()));
        return button;
    }

    private TextArea createOutputArea(){
        return new TextArea("",OUTPUT_HEIGHT,OUTPUT_WIDTH,TextArea.SCROLLBARS_NONE);
    }

    private void showInventory() {
        gameInventory.setText(game.getAdventure().commandInventory());
    }

    private void startTheGame(){
        InputStream inputStream=Game.class.getClassLoader().getResourceAsStream("default.json");
        game.setAdventure(game.generateAdventure(game.loadAdventureJson(inputStream)));
        game.setParser(new Parser(game.getAdventure()));
        roomWelcome();
    }

    private void roomWelcome(){
        gameOutput.append("Room: "+game.getAdventure().getCurrentRoom().toString()+"\n");
        gameOutput.append(game.getAdventure().listRoomItems()+"\n");
        showInventory();
    }

    private JMenuBar createDropdown(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem one = new JMenuItem("Save");
        menu.add(one);
        one.addActionListener(listen->game.getAdventure().save("AdventureSave.ser"));
        JMenuItem two = new JMenuItem("Load");
        menu.add(two);
        two.addActionListener(listen->loadAdventureButton());
        JMenuItem three = new JMenuItem("Change Player Name");
        menu.add(three);

        return menuBar;
    }

    private void setMainContainer(){
        setUpSize();
        gameInventory=new TextArea("",INVENTORY_HEIGHT,INVENTORY_WIDTH,TextArea.SCROLLBARS_NONE);
        GridBagConstraints c = new GridBagConstraints();
        contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        gameInput = createInputArea();
        gameOutput= createOutputArea();
        gameInventory.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.LINE_AXIS));

        c.fill=GridBagConstraints.HORIZONTAL;

        //Dropdown menu
        c.gridx=0;
        c.gridy=0;
        contentPane.add(createDropdown(),c);

        //Game output window
        c.gridx=0;
        c.gridy=1;
        contentPane.add(gameOutput,c);

        //Inventory window
        c.gridx=1;
        c.gridy=1;
        contentPane.add(gameInventory,c);

        //input panel
        inputPanel.add(createCommandButton(),c);
        inputPanel.add(gameInput,c);

        c.gridx=0;
        c.gridy=2;
        contentPane.add(inputPanel,c);

    }

    private void loadAdventureButton(){
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result==JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            game.setAdventure(game.generateAdventure(game.loadAdventureJson(selectedFile.getAbsolutePath())));
        }
        gameOutput.append("Loaded new adventure\n");
        roomWelcome();
    }

    private void gameAction(String str){
        try{
            Command command = game.getParser().parseUserCommand(str);
            gameOutput.append(game.getAdventure().executeCommand(command));
        }catch (InvalidCommandException e){
            gameOutput.append("Usage Error - Type 'help' for a list of commands.");
        }
        gameOutput.append("\n");
        roomWelcome();
        gameInput.setText("");
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
