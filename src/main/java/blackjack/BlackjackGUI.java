package blackjack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class BlackjackGUI extends JFrame {

    private final JPanel dealerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
    private final JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
    private final JLabel statusLabel = new JLabel("Welcome to Blackjack");
    private final JButton hitBtn = new JButton("Hit");
    private final JButton stayBtn = new JButton("Stay");
    private final JButton newGameBtn = new JButton("New Game");

    private final JLabel scoreboardLabel = new JLabel("Player: 0 | Dealer: 0 | Rounds: 0");

    private Deck deck;
    private Player player;
    private Player dealer;

    private final String cardImageFolder = "src/resources/cards/";
    private final String cardBackImage = "src/resources/cards/back.png"; // Add a back.png image

    private boolean roundOver = false;
    private boolean revealDealerCard = false;

    private int playerWins = 0;
    private int dealerWins = 0;
    private int totalRounds = 0;

    public BlackjackGUI() {
        setTitle("Blackjack - Casino Table");
        setSize(880, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JPanel table = new JPanel(new BorderLayout(12, 12));
        table.setBorder(new EmptyBorder(12, 12, 12, 12));
        table.setBackground(new Color(22, 120, 50));

        // Dealer area
        JPanel dealerArea = new JPanel(new BorderLayout());
        JLabel dealerLabel = new JLabel("Dealer");
        dealerLabel.setForeground(Color.BLACK);
        dealerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        dealerArea.add(dealerLabel, BorderLayout.NORTH);
        dealerPanel.setOpaque(false);
        dealerArea.add(dealerPanel, BorderLayout.CENTER);

        // Player area
        JPanel playerArea = new JPanel(new BorderLayout());
        JLabel playerLabel = new JLabel("Player");
        playerLabel.setForeground(Color.BLACK);
        playerLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        playerArea.add(playerLabel, BorderLayout.NORTH);
        playerPanel.setOpaque(false);
        playerArea.add(playerPanel, BorderLayout.CENTER);

        table.add(dealerArea, BorderLayout.NORTH);
        table.add(playerArea, BorderLayout.SOUTH);
        add(table, BorderLayout.CENTER);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        controlPanel.setOpaque(false);

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setForeground(Color.BLACK);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreboardLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        scoreboardLabel.setForeground(Color.BLACK);
        scoreboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        hitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        stayBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        controlPanel.add(statusLabel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        controlPanel.add(scoreboardLabel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 24)));
        controlPanel.add(hitBtn);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        controlPanel.add(stayBtn);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        controlPanel.add(newGameBtn);

        add(controlPanel, BorderLayout.EAST);

        hitBtn.addActionListener(e -> hit());
        stayBtn.addActionListener(e -> stay());
        newGameBtn.addActionListener(e -> startNewGame());

        startNewGame();
    }

    private void startNewGame() {
        deck = new Deck();
        deck.shuffleDeck(200);
        player = new Player("Player");
        dealer = new Player("Dealer");

        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        revealDealerCard = false;
        roundOver = false;

        hitBtn.setEnabled(true);
        stayBtn.setEnabled(true);
        statusLabel.setText("Your move...");

        refreshTable();
    }

    private void hit() {
        if (roundOver) return;

        player.addCard(deck.dealCard());
        refreshTable();

        if (player.getTotalScore() > 21) {
            endRound("You busted! Dealer wins.");
            dealerWins++;
        }
    }

    private void stay() {
        if (roundOver) return;

        revealDealerCard = true;

        while (dealer.getTotalScore() < 17) {
            dealer.addCard(deck.dealCard());
            try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        }

        int playerScore = player.getTotalScore();
        int dealerScore = dealer.getTotalScore();
        String result;

        if (dealerScore > 21) {
            result = "Dealer busted! You win!";
            playerWins++;
        } else if (dealerScore == playerScore) {
            result = "Push (tie).";
        } else if (playerScore > dealerScore) {
            result = "You win!";
            playerWins++;
        } else {
            result = "Dealer wins.";
            dealerWins++;
        }

        endRound(result);
    }

    private void endRound(String message) {
        roundOver = true;
        revealDealerCard = true;
        totalRounds++;

        hitBtn.setEnabled(false);
        stayBtn.setEnabled(false);

        statusLabel.setText(message);
        updateScoreboard();
        refreshTable();
    }

    private void updateScoreboard() {
        scoreboardLabel.setText(
            String.format("Player: %d | Dealer: %d | Rounds: %d", playerWins, dealerWins, totalRounds)
        );
    }

    private void refreshTable() {
        dealerPanel.removeAll();
        playerPanel.removeAll();

        // Dealer cards
        List<Card> dealerCards = dealer.playerCards;
        for (int i = 0; i < dealerCards.size(); i++) {
            if (i == 0 && !revealDealerCard) {
                dealerPanel.add(createCardLabelFromFile(cardBackImage));
            } else {
                dealerPanel.add(createCardLabelFor(dealerCards.get(i)));
            }
        }

        // Player cards
        for (Card c : player.playerCards) {
            playerPanel.add(createCardLabelFor(c));
        }

        dealerPanel.revalidate();
        dealerPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private JLabel createCardLabelFor(Card card) {
        ImageIcon icon = loadCardImageIcon(card);
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            JLabel lbl = new JLabel(new ImageIcon(scaled));
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            return lbl;
        } else {
            JLabel lbl = new JLabel(card.toString(), SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(100, 140));
            lbl.setOpaque(true);
            lbl.setBackground(Color.WHITE);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            return lbl;
        }
    }

    private JLabel createCardLabelFromFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            JLabel lbl = new JLabel(new ImageIcon(scaled));
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            return lbl;
        } else {
            JLabel lbl = new JLabel("🂠", SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(100, 140));
            lbl.setOpaque(true);
            lbl.setBackground(Color.GRAY);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            return lbl;
        }
    }

    private ImageIcon loadCardImageIcon(Card card) {
        String rank = card.rank.toString();
        String suit = card.suit.toString();
        String fileName = rank + "-" + suit + ".png";
        String fullPath = cardImageFolder + fileName;

        File f = new File(fullPath);
        if (f.exists()) {
            return new ImageIcon(fullPath);
        }

        // Try classpath fallback
        java.net.URL url = getClass().getClassLoader().getResource("cards/" + fileName);
        if (url != null) {
            return new ImageIcon(url);
        }

        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackjackGUI().setVisible(true));
    }
}
