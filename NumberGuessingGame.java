import java.io.InputStreamReader;
import java.util.*;
import static java.awt.Color.MAGENTA;
import java.awt.LayoutManager;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
//Creates a Player Class for every New Turn

class Player{
    private String name;
    private int score;
    Player(String n){
        name = n;
        this.score = 0;
	}
	Player(String n, int s){
		name = n;
		score = s;
	}
	Player(){
		name = "";
		score = 0;
	}
    void setName(String name) { this.name = name; }
	void setScore(int tries)  { this.score = tries;}
    String getName() { return name; }
    int getScore() { return score; }
}

class ScoreComparator implements Comparator<Player>{
    @Override
    public int compare(Player o1, Player o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
	}
}

//----------------------------------------------------------------------------------------------------------------------------------

class HallOfFame{
	public void hallOfFameWrite(Player p) throws IOException {
		try{
			String path = "halloffame.txt";
			FileWriter file = new FileWriter(path, true);
			//If file doesn't exists, it will automatically be created. True = open in append mode.
			PrintWriter writer = new PrintWriter(file);
			String name = p.getName();
			int tries = p.getScore();
			writer.println(name);
			writer.println(tries);
			writer.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
/*
Now, we enter the Hall of fame class, which contain three methods, hallOfFameWrite, to write the contents, namely player name and player's score in the file. We store the player name in odd numbered lines, line 1, 3, 5... and players score in even numbered lines, line 2, 4, 6...
hallOfFameWrite takes a player type object as a parameter. For each player we are creating a seperate object, in which we are storing the details of the player. The string path is the path of the file, we put it in the same directory as our program.
Next, we open the file in append mode by providing the second argument of the FileWriter as 'true'.
We use the methods of player class, p.getName() to return the name and p.getScore() to return the score of the player, and store them in variables name and tries. We then write it in the file and close the file, along with suitable exception handling.
*/
	public void hallOfFameReset() throws IOException{
			//Delete the Previous Content(Overwrites the File with an empty string)
			FileWriter file = new FileWriter("halloffame.txt");
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();	
	}
/*
To clean up the file "hallOfFame.txt", we made a hallOfFameReset() method, which will simply replace the contents of the file with a blank empty string, and then close the file.
		1. Reads the file "hallOfFame.txt"
		2. Stores the entire data to ArrayList of Player Object
		3. Sorts the ArrayList according to the Score //Ascending Order
		4. Deletes the contents of the File
		5. Prints Out the Contents of the file
		6. Writes the Username & Score to File
*/
	public void hallOfFameRead() throws IOException {
		try{
			String path = "halloffame.txt";
			BufferedReader in = new BufferedReader( new InputStreamReader(new FileInputStream(path), "UTF-8"));
			ArrayList<Player> l = new ArrayList<Player>();
			String str;
			int linenum = 1;
			int num = 0;
			String str1 = "";
			int flag = 0;
			while ((str = in.readLine()) != null) {	
				if(linenum %2 == 0) {
					num = Integer.parseInt(str);
					flag = 1;
				}
				else{
					str1 = str;
				}
				if(flag == 1){
					l.add(new Player(str1, num));
					flag = 0;
				}
				linenum++;
			}
			in.close();
			Collections.sort(l, new ScoreComparator());
			ListIterator<Player> itr = l.listIterator();
			while(itr.hasNext()){
				Player p = (Player)itr.next();
				System.out.println(p.getScore() +" -> "+p.getName());    
			}
			hallOfFameReset();
			itr = l.listIterator();
			int k = 0;
			while(itr.hasNext() && k<5){
				Player p = (Player) itr.next();
				hallOfFameWrite(p);
				k++;
			}
			System.out.println("\n");
			in.close();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
/*
This is the most important method where we read the hall of fame. We open the file in read mode using the desired UTF-8 encoding.
Next, we declare an ArrayList of type player, which can basically allocate player type objects dynamically.
Next, we initialize our required variables. Linenum variable will keep a track of the line number.
If linenum is odd, it means we are reading the players name, so we store it in string 'str1'.
If linenum is even, it means we are reading the players score, so we store it in an integer variable 'num'.
If we obtain both the name and the corresponding score, the flag variable is set to 1, and we add those details inside the arraylist. The flag is made to 0 again.
After that we sort the array list. Since it is not an array, we need collections.sort(). java.util.Collections.sort() method is present in java.util.Collections class. It is used to sort the elements present in the specified list of Collection in ascending order.
In order to define a custom logic for sorting, which is different from the natural ordering of the elements, we can implement the java.util.Comparator interface and pass an instance of it as the second argument of sort().
Next, we declare a list iterator. A list iterator is similar to the index in an array. It is used to iterate over the ArrayList. Our list iterator is of the type <Player>, indicating that we will use this to iterate over arraylist storing player type objects.
While itr.hasNext(), meaning while the iterator has some next element, it will return the element via itr.next(), and it will be typecast to Player type, and is stored in a player type object p. We print the score and name via the getter methods declared in player class.
Next, we reset the hall of fame and erase all its contents. We re-initialize the iterator.
Remember that our array list is now sorted, as we have already sorted it before. So we will simply iterate over the first five entries of the array list, and we will write this content on the Hall Of Fame file via the hallOfFameWrite() function. The number of entries is denoted by the variable k.
Then we simply close the file handle.
*/
}

public class NumberGuessingGame{
	static int tries = 0;
	public static void main(String[] args){
		showMenu();
	}
	
	static void showMenu(){
		JFrame home = new JFrame("Number Guessing Game");
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//home.setLocationRelativeTo(null); //Centered
		JLabel intro = new JLabel("Welcome to Number Guessing Game");
		intro.setBounds(100,50,400, 40);
		JButton btnNewGame = new JButton("New Game");
		JButton info = new JButton(new AbstractAction("Creators"){
			public void actionPerformed(ActionEvent click){
				JFrame profile=new JFrame("Creators of application");
				profile.setSize(600,600);
				profile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JLabel l1 = new JLabel("Crafted in TMSL, Kolkata under the guidance of Prof. U Das by the ");
				l1.setBounds(25,50,550,60);
				JLabel l2 = new JLabel("Dept. of Computer Science and Engineering in 2019 by the following students: ");
				l2.setBounds(25,80,580,60);
				profile.add(l2);
				profile.add(l1);
				profile.add(l1);
				JLabel l5 = new JLabel("Aditya Gaddhyan");
				l5.setBounds(150,120,400,60);
				profile.add(l5);
				JLabel ll5 = new JLabel("Aakash Singh");
				ll5.setBounds(150,150,400,60);
				profile.add(ll5);
				JLabel lq5 = new JLabel("Apratim Sen");
				lq5.setBounds(150,180,400,60);
				profile.add(lq5);
				JLabel lw5 = new JLabel("Ankur Kumar");
				lw5.setBounds(150,210,400,60);
				profile.add(lw5);
				JLabel lw53=new JLabel("   ");
				lw53.setBounds(150,240,400,60);
				profile.add(lw53);
				profile.setVisible(true);
				profile.setLayout(null);
		}
	});
		info.setBounds(100,300,200,30);
		home.add(info);
		btnNewGame.setBounds(100, 150, 200, 30);
		btnNewGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tries=0;
				String name = JOptionPane.showInputDialog("Please input name: ");
				Player p = new Player(name);
				home.setVisible(false);
				startGame(p);
			}
		});
		home.add(btnNewGame);
		JButton b2 = new JButton(new AbstractAction("Hall Of Fame: "){
			@Override
			public void actionPerformed(ActionEvent e){
				JFrame frame = new JFrame("Hall Of Fame");
			    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				JLabel ll1 = new JLabel("Name                           Tries");
				ll1.setBounds(140,80,200,30);
				frame.add(ll1);
				try(BufferedReader br = new BufferedReader(new FileReader(new File("halloffame.txt")))) {
					String text = null;
					String text2 = null;
					int y = 110;
					//---------------------------------------------------------------------------------------------------------------------------------
					ArrayList<Player> l = new ArrayList<Player>();
					//An array of Player objects created. setSize
					String str;
					int linenum = 1;
					//Player p = new Player();
					int num = 0;
					String str1 = "";
					int flag = 0;
					while ((str = br.readLine()) != null) {	
						if(linenum %2 == 0) {
							num = Integer.parseInt(str);
							flag = 1;
						}
						else{
							str1 = str;
						}
						if(flag == 1){
							l.add(new Player(str1, num));
							flag = 0;
						}
						linenum++;
					}
					br.close();
					Collections.sort(l, new ScoreComparator());
					ListIterator<Player> itr = l.listIterator();
					int c = 1;
					while(itr.hasNext() && c <= 5){
						Player p = (Player)itr.next();
						//System.out.println(p.getScore() +" -> "+p.getName());    
					//----------------------------------------------------------------------------------------------------------------------------------
						if((text = p.getName()) != null && (text2 = p.getScore()+"") != null) {
							JLabel ll2 = new JLabel(text+"                           "+text2);
							y += 50;
							ll2.setBounds(140,y,200,30);
							frame.add(ll2);
							c++;
					//	}
					//}
						//JLabel ll2 = new JLabel("Games are played not to get ranked but to enjoy.");
						//ll2.setBounds(30,y+50,1000,30);
						//frame.add(ll2);
						//JLabel ll3 = new JLabel("We were so busy enjoying that we forgot to build the ranking system. Sorry:D");
						//ll3.setBounds(30,y+80,1000,30);
						//frame.add(ll3);
						}
					}
				} 
				catch (IOException ex) {
				    ex.printStackTrace();
				}
				frame.setSize(500,500);
				frame.setLayout(null);
				frame.setVisible(true);
			}
		});
		b2.setBounds(100, 200, 200, 30);
		home.add(b2);
		home.add(intro);
		home.setSize(500,500);
		home.setLayout(null);
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home.setVisible(true);
	}

	static void startGame(Player p){
		JFrame game = new JFrame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setLayout(new FlowLayout(FlowLayout.CENTER));
		game.setSize(500,500);
		JLabel lab = new JLabel("Input a number: ");
		game.add(lab);
		int maxnum = 0;
		boolean flag = true;
		while(flag) {
			try {
				maxnum = Integer.parseInt(JOptionPane.showInputDialog("Enter the maximum number: "));
				flag = false;
			} catch(Exception e) {}
		}
		Random rand = new Random();
		int number = 1 + rand.nextInt(maxnum);
		JTextField inputBox = new JTextField(40);
		inputBox.setBounds(50,50,40,30);
		game.add(inputBox);
		lab.setText("Guess a number between 1 and "+ maxnum + ": ");
		//ll2
		JLabel triesLabel = new JLabel();
		game.add(triesLabel);
		JLabel temp = new JLabel("Answer: "+Integer.toString(number)+" Tries: "+Integer.toString(tries));
		JButton check = new JButton(new AbstractAction("Check"){
				@Override
				public void actionPerformed(ActionEvent e){
					tries=tries+1;
					temp.setText("Tries : "+Integer.toString(tries));
					temp.setBounds(100,100,30,30);
					game.add(temp);
					
					int guess = Integer.parseInt(inputBox.getText());
						if (guess == number){
							JFrame won = new JFrame("You won!");
							won.setSize(500,500);
							JLabel won_l1=new JLabel("Hurray! You won.");
							won_l1.setBounds(150,50,500,30);		
							won.add(won_l1);
							game.setVisible(false);
							JLabel won_l2=new JLabel("You took "+Integer.toString(tries)+" guesses to win.");
							won_l2.setBounds(150,150,500,30);
							won.add(won_l2);
							JButton go_back=new JButton(new AbstractAction("Main Menu"){
								@Override
								public void actionPerformed(ActionEvent e){
									won.setVisible(false);
									showMenu();
								}
							});
							won.setLayout(null);
							go_back.setBounds(150,400,10,50);
							won.add(go_back);
							won.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							won.setVisible(true);
							go_back.setBounds(150,250,150,75);
							p.setScore(tries);
						try{
							HallOfFame hof = new HallOfFame();
							hof.hallOfFameWrite(p);
						}
						catch(Exception t){
							triesLabel.setText("Unexpected Error!");
						}
						}
						else{
							if(guess > number){
								if(guess > number + 2)
								triesLabel.setText("Number too high!");
								else
								triesLabel.setText("Number is too high! But you are close!");
							}
							else{
								if(guess < number - 2)
								triesLabel.setText("Number too low!");
								else
								triesLabel.setText("Number is too low! But you are close!");
							}
							inputBox.setText("");
							game.setVisible(true);
						}
				}
			});
		game.add(check);
		game.setVisible(true);
			 
	}
}
