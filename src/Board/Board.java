/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Board.Door.DoorType;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Occupant.Occupant;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 *
 * @author wilson
 */
public class Board {

    private int x;

    private int y;

    // map location
    private String map;

    private int columns, rows;

    private Tile[][] sqs;

    private int tileSize;

    private ImageIcon tree;

    private ImageIcon grass;

    private ImageIcon water;

    private ImageIcon door;

    public Board(String map) {
        if (map == null || map.length() == 0) {
            throw new IllegalArgumentException("not a valid map");
        }
        this.map = map;
        this.tileSize = 30;
        this.tree = new ImageIcon("Images/Tree2.png");
        this.grass = new ImageIcon("Images/Grass.png");
        this.water = new ImageIcon("Images/Water.png");
        this.door = new ImageIcon("Images/Door.png");
        scanMap();
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getNumRows() {
        return rows;
    }

    public int getNumColumns() {
        return columns;
    }

    public void scanMap() {
        try {
            ArrayList<String> mCode = new ArrayList<>();
            Scanner sc = new Scanner(new File(map));
            while (sc.hasNext()) {
                mCode.add(sc.nextLine());
            }
            sc.close();

            columns = mCode.get(0).length();
            rows = mCode.size();
            sqs = new Tile[rows][columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    Position pos = new Position(row, col);
                    char squareID = mCode.get(row).charAt(col);
                    Tile square = setUpSquare(pos, squareID);

                    if (square != null) {
                        sqs[row][col] = square;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Tile setUpSquare(Position pos, char c) {
        Tile square = null;
        //Position pos = new Position(row, col);
        switch (c) {
            case '1':
                square = new Tree(pos, Color.green);
                break;
            case '0':
                square = new Grass(pos, Color.WHITE);
                break;
            case '2':
                square = new Water(pos, Color.BLUE);
                break;
            case '3':
                square = new Door(DoorType.Bronze, pos, Color.YELLOW);
                break;
            case '4':
                square = new Door(DoorType.Silver, pos, Color.GRAY);
                break;
        }

        return square;
    }

    public Tile getSquare(Position pos) {
        return sqs[(int) pos.getRow()][(int) pos.getColumn()];
    }

    public void printBoard() {

        //String printing = "";
        final int PAD = 1;
        for (Tile[] row : sqs) {
            for (Tile square : row) {
                String str = square.getString();
                String occ = "";
                if (square.hasOccupant()) {
                    occ = square.getOccupant().getString();
                }
                String output = str;
                output = occ;
                while (output.length() < PAD) {
                    output += str;
                }
                System.out.print(output);
                //printing += output;
            }
            System.out.println();
            //printing += "\n";
        }
        //return printing;
    }

    public Position getRandomPosition(int minX, int maxX, int minY, int maxY) {
        Random random = new Random();
        int rangeX = maxX - minX + 1;
        int x = random.nextInt(rangeX) + minX;
        int rangeY = maxY - minY + 1;
        int y = random.nextInt(rangeY) + minY;
        if (isValidPosition(new Position(x, y))) {
            return new Position(x, y);
        }
        return getRandomPosition(minX, maxX, minY, maxY);
    }

    public boolean isValidPosition(Position pos) {
        Tile x = getSquare(pos);
        if (x instanceof Door) {
            Door b = (Door) x;
            return b.isLock();
        }
        return !(x instanceof Tree || x instanceof Water || x.getOccupant() != null);
    }

    public void addOccupant(Occupant occ) {
        if (occ != null) {
            Tile x = getSquare(occ.getPosition());
            if (!x.hasOccupant()) {
                x.addOccupant(occ);
            }
        }

    }

    public void draw(Graphics2D g) {
        for (int row = 0; row < sqs.length; ++row) {
            for (int col = 0; col < sqs[row].length; ++col) {
                if ("T".equals(sqs[row][col].getString())) {
                    g.drawImage(tree.getImage(), x + col * tileSize, y + row * tileSize, null);
                } else if ("W".equals(sqs[row][col].getString())) {
                    g.drawImage(water.getImage(), x + col * tileSize, y + row * tileSize, null);
                } else if (".".equals(sqs[row][col].getString())) {
                    g.drawImage(grass.getImage(), x + col * tileSize, y + row * tileSize, null);
                } else if ("Q".equals(sqs[row][col].getString()) || "R".equals(sqs[row][col].getString()) || "J".equals(sqs[row][col].getString())) {
                    g.drawImage(door.getImage(), x + col * tileSize, y + row * tileSize, null);
                }
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position calculateDestination(Position from, Direction moveDirection) {
        Position destination = null;

        try {
            int row = from.getRow();
            int col = from.getColumn();

            switch (moveDirection) {
                case NORTH:
                    row -= 1;
                    break;
                case EAST:
                    col += 1;
                    break;
                case SOUTH:
                    row += 1;
                    break;
                case WEST:
                    col -= 1;
                    break;
            }
            destination = new Position(row, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destination;
    }

    public Position validToMove(Position from, Direction direction) {
        Position destination = calculateDestination(from, direction);
        if ((destination != null) && (wallDetect(destination) || occDetect(destination) != null)) {
            destination = null;
        }
        return destination;
    }

    public boolean wallDetect(Position destinationTemp) {

        boolean treeInFront = getSquare(destinationTemp) instanceof Tree;
        boolean waterInFront = getSquare(destinationTemp) instanceof Water;
        Tile square = getSquare(destinationTemp);

        if (square instanceof Door) {
            Door x = (Door) square;
            return x.isLock();
        }
        return (treeInFront || waterInFront);
    }

    public Occupant occDetect(Position destinationTemp) {
        return getSquare(destinationTemp).getOccupant();
    }
    
    
}
