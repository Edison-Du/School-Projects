/**
 * [GameFrame.java]
 * A top-down dungeon crawler shooter game complete with art and music
 * @author Edison Du
 * @version 1.0 June 15, 2021
 **/

/******* Graphics and GUI imports *******/
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/******* Keyboard imports *******/
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/******* Mouse imports *******/
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/******* Window imports *******/
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/******* Sound imports *******/
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/******* Input and Output imports *******/
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.PrintWriter;

/******* File imports *******/
import java.io.File;
import java.io.FileNotFoundException;

/******* Utility imports *******/
import java.util.ArrayList;
import java.util.Random;



public class GameFrame extends JFrame { 
  
  /*****************************************************/
  /*================= Class Variables =================*/
  /*****************************************************/
  
  /***** Window Dimensions *****/
  static final int WINDOW_WIDTH = 820;
  static final int WINDOW_HEIGHT = 840;
  
  /***** GUI and Listeners *****/
  static GameAreaPanel gamePanel;
  static MyKeyListener keyListener;
  static MyMouseListener mouseListener;
  static MyMouseMotionListener mouseMotionListener;
  static MyWindowListener windowListener;
  
  /***** Program Logic *****/
  static GameState gameState;
  static MenuState menuState;
  
  /***** Fonts *****/
  static Font font;
    
  /***** Images *****/
  static BufferedImage mainMenuScreen;
  static BufferedImage statisticsScreen;
  static BufferedImage helpScreen;
  static BufferedImage victoryScreen;
  static BufferedImage defeatScreen;
  static BufferedImage heartImage;
  static BufferedImage keyImage;
  static BufferedImage shieldImage;
  
  /***** Music *****/
  static Clip menuMusic;
  static Clip gameMusic;
  static Clip bossMusic;
  
  /***** Sound Effects *****/
  static Clip keyPickUp;
  static Clip gunPickUp;
  static Clip healthPickUp;
  static Clip clickSound;
  static Clip unlockedBoss;
  static Clip roomLocked;
  static Clip roomOpened;
  static Clip damageTaken;
  static Clip enemyDeath;
  static Clip playerDeath;
  static Clip bossDeath;
  static Clip playerBasicGunShot;
  static Clip playerShotgunShot;
  static Clip playerMachineGunShot;
  static Clip playerClearGunShot;
  static Clip playerWaveGunShot;
  static Clip enemyBasicGunShot;
  static Clip enemyShotgunShot;
  static Clip enemyMachineGunShot;
  static Clip enemyClearGunShot;
  static Clip enemyWaveGunShot;
  static Clip bossAttack1;
  static Clip bossAttack2;
  static Clip bossAttack3;
  static Clip bossAttack4;
  static Clip bossAttack5;
  static Clip bossAttack6;
  
  /***** Statistics *****/
  static int totalBulletsShot=0;
  static int totalBulletsHit=0;
  static int totalEnemiesKilled=0;
  static int totalDamageTaken=0;
  static int totalRoomsCleared=0;
  static int totalVictories=0;
  static int totalDefeats=0;
  
  /***** Game Logic *****/
  static Map map;
  static Player player;
  static Boss boss;
  static int bossAttackType;
  static int bossAttackTimer;
  static Random random = new Random();
  
  
  
  /***********************************************************/
  /*================= GameFrame Constructor =================*/
  /***********************************************************/
  GameFrame() {
    super("Liminal Shooter");       
    
    // Configure Frame    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    this.setResizable(false);

    // Add game panel
    gamePanel = new GameAreaPanel();    
    this.add(new GameAreaPanel());    
    this.requestFocusInWindow();
    this.setVisible(true);   
    
    // Add listeners
    keyListener = new MyKeyListener();  
    this.addKeyListener(keyListener);    
    mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);  
    mouseMotionListener = new MyMouseMotionListener();
    this.addMouseMotionListener(mouseMotionListener);
    windowListener = new MyWindowListener();
    this.addWindowListener(windowListener);

    gameState = GameState.MENU;
    menuState = MenuState.DEFAULT;
    
    // Pre-load the sprites for objects
    Room.loadSprites();
    Player.loadSprite();
    BasicEnemy.loadSprite();
    ShotgunEnemy.loadSprite();
    MachineGunEnemy.loadSprite();
    ClearGunEnemy.loadSprite();
    WaveGunEnemy.loadSprite();
    Boss.loadSprites();
    BasicGun.loadSprites();
    Shotgun.loadSprites();
    MachineGun.loadSprites();
    ClearGun.loadSprites();
    WaveGun.loadSprites();
    Projectile.loadSprites();
    ExplosiveProjectile.loadSprite();
    MedicKit.loadSprite();
    Key.loadSprite();
    BossPortal.loadSprites();
    
    // Load sprites for UI
    mainMenuScreen = loadSprite("Sprites/screen_main_menu.png");
    statisticsScreen = loadSprite("Sprites/screen_statistics.png");
    helpScreen = loadSprite("Sprites/screen_help.png");
    victoryScreen = loadSprite("Sprites/screen_victory.png");
    defeatScreen = loadSprite("Sprites/screen_defeat.png");
    heartImage = loadSprite("Sprites/heart.png");
    keyImage = loadSprite("Sprites/key_icon.png");
    shieldImage = loadSprite("Sprites/shield.png");

    // Load music files
    menuMusic = loadAudio("Music/menu_music.wav");
    gameMusic = loadAudio("Music/game_music.wav");
    bossMusic = loadAudio("Music/boss_music.wav");
    
    // Load sound effect files
    clickSound = loadAudio("Sound Effects/menu_click.wav");
    keyPickUp = loadAudio("Sound Effects/pick_up_key.wav");
    gunPickUp = loadAudio("Sound Effects/pick_up_gun.wav");
    healthPickUp = loadAudio("Sound Effects/pick_up_health.wav");
    unlockedBoss = loadAudio("Sound Effects/unlock_boss.wav");
    roomLocked = loadAudio("Sound Effects/room_locked.wav");
    roomOpened = loadAudio("Sound Effects/room_cleared.wav");
    damageTaken = loadAudio("Sound Effects/damage_taken.wav");
    enemyDeath = loadAudio("Sound Effects/death_enemy.wav");
    playerDeath = loadAudio("Sound Effects/death_player.wav");
    bossDeath = loadAudio("Sound Effects/death_boss.wav");
    playerBasicGunShot = loadAudio("Sound Effects/gun_shot_player_basic.wav");
    playerShotgunShot = loadAudio("Sound Effects/gun_shot_player_shotgun.wav");
    playerMachineGunShot = loadAudio("Sound Effects/gun_shot_player_machine.wav");
    playerClearGunShot = loadAudio("Sound Effects/gun_shot_player_clear.wav");
    playerWaveGunShot = loadAudio("Sound Effects/gun_shot_player_wave.wav");
    enemyBasicGunShot = loadAudio("Sound Effects/gun_shot_enemy_basic.wav");
    enemyShotgunShot = loadAudio("Sound Effects/gun_shot_enemy_shotgun.wav");
    enemyMachineGunShot = loadAudio("Sound Effects/gun_shot_enemy_machine.wav");
    enemyClearGunShot = loadAudio("Sound Effects/gun_shot_enemy_clear.wav");
    enemyWaveGunShot = loadAudio("Sound Effects/gun_shot_enemy_wave.wav");
    bossAttack1 = loadAudio("Sound Effects/boss_attack-1.wav");
    bossAttack2 = loadAudio("Sound Effects/boss_attack-2.wav");
    bossAttack3 = loadAudio("Sound Effects/boss_attack-3.wav");
    bossAttack4 = loadAudio("Sound Effects/boss_attack-4.wav");
    bossAttack5 = loadAudio("Sound Effects/boss_attack-5.wav");
    bossAttack6 = loadAudio("Sound Effects/boss_attack-6.wav");
    
    // Load font file
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/Candara.ttf"));
      font = font.deriveFont(Font.PLAIN, 36);
    } catch (FileNotFoundException e) {
      System.out.println("No file by the name of 'Fonts/Candara.ttf' exists.");
    } catch (FontFormatException e) {
      System.out.println("Error loading the file 'Fonts/Candara.ttf'.");
    } catch (IOException e) {
      System.out.println("Error loading the file 'Fonts/Candara.ttf'.");
    }
    
    // Read statistics from file
    try {
      Scanner fileInput = new Scanner(new File("statistics.txt"));
      totalBulletsShot = fileInput.nextInt();
      totalBulletsHit = fileInput.nextInt();
      totalEnemiesKilled = fileInput.nextInt();
      totalDamageTaken = fileInput.nextInt();
      totalRoomsCleared = fileInput.nextInt();
      totalVictories = fileInput.nextInt();
      totalDefeats = fileInput.nextInt();
      fileInput.close();
    } catch (FileNotFoundException e) {
      System.out.println("No file by the name of 'statistics.txt' exists.");
    } catch (InputMismatchException e) {
      System.out.println("'statistics.txt' is not in proper format, all values reset to 0.");
    } catch (NoSuchElementException e) {
      System.out.println("'statistics.txt' is not in proper format, all values reset to 0.");
    }
    
    // Start menu music
    if (menuMusic != null) {
      playMusic(menuMusic);
    }

    // Start the game loop   
    Thread t = new Thread(new Runnable(){     
      public void run() { 
        animate(); 
      }
    }); 
    
    t.start();       
  } 
  /********************************* End of GameFrame Constructor *********************************/
  

  
  /*************************************************/
  /*================= Main Method =================*/
  /*************************************************/
  public static void main(String[] args) { 
    EventQueue.invokeLater(() -> {
      GameFrame x = new GameFrame();
      x.setVisible(true);
    }); 
  }
  /********************************* End of Main *********************************/
  

  
  /**
   * animate
   * This method contains the game loop
   */
  public void animate() {
    
    while(true){
      
      if (gameState == GameState.IN_GAME) {

        /********************************************** Variables **********************************************/
        
        Room currentRoom = map.getCurrentRoom();
        boolean[][] roomCleared = map.getRoomCleared();

        ArrayList<Rectangle> walls = currentRoom.getWalls();
        ArrayList<Enemy> enemies = currentRoom.getEnemies();
        ArrayList<Projectile> playerBullets = currentRoom.getPlayerBullets();
        ArrayList<Projectile> enemyBullets = currentRoom.getEnemyBullets();
        
        int xBoundary = currentRoom.getColumns() * currentRoom.getChunkSize();
        int yBoundary = currentRoom.getRows() * currentRoom.getChunkSize();
        
        Gun playerGun = player.getGun();
        
        int newPlayerX, newPlayerY;
        boolean playerCanMove = true, playerCanMoveX = true, playerCanMoveY = true;
        Rectangle playerMoved, playerMovedX, playerMovedY;
        
        /********************************************** Player Movement and Shooting **********************************************/
        
        /* Get the collision boxes of the player if it were to move
         * If any of these collision boxes intersect with a wall/enemy/boss, then the player will not in that direction
         */
        
        player.calculateDirections();
        
        newPlayerX = player.getX() + player.getXDirection();
        newPlayerY = player.getY() + player.getYDirection();
        
        playerMovedX = new Rectangle(newPlayerX,    player.getY(), player.getWidth(), player.getHeight());
        playerMovedY = new Rectangle(player.getX(), newPlayerY,    player.getWidth(), player.getHeight());
        playerMoved  = new Rectangle(newPlayerX,    newPlayerY,    player.getWidth(), player.getHeight());
        
        // Player-Enemy collision
        if (boss == null) {
          for (int i = 0; i < enemies.size(); i++) {
            if (playerMovedX.intersects(enemies.get(i).getCollisionBox())) {
              playerCanMoveX = false;
            }
            if (playerMovedY.intersects(enemies.get(i).getCollisionBox())) {
              playerCanMoveY = false;
            }
            if (playerMoved.intersects(enemies.get(i).getCollisionBox())) {
              playerCanMove = false;
            }
          }
          
        // Player-Boss collision
        } else {
          if (playerMovedX.intersects(boss.getCollisionBox())) {
            playerCanMoveX = false;
          }
          if (playerMovedY.intersects(boss.getCollisionBox())) {
            playerCanMoveY = false;
          }
          if (playerMoved.intersects(boss.getCollisionBox())) {
            playerCanMove = false;
          }
        }
        
        // Player takes damage if moved into enemy/boss
        if (!(playerCanMove && playerCanMoveY && playerCanMoveX)) {
          if (!player.isInvincible()) {
            playSound(damageTaken);
            player.changeHealth(-1);
            player.setInvincibility(100);
            totalDamageTaken++;
          }
        }
       
        // Player-Wall collision
        for (int i = 0; i < walls.size(); i++) {
          if (playerMovedX.intersects(walls.get(i))) {
            playerCanMoveX = false;
          }
          if (playerMovedY.intersects(walls.get(i))) {
            playerCanMoveY = false;
          }
          if (playerMoved.intersects(walls.get(i))) {
            playerCanMove = false;
          }
        }

        // Move the player
        if (playerCanMove) {
          player.move();
          playerGun.setTarget(playerGun.getTargetX() + player.getXDirection(), playerGun.getTargetY() + player.getYDirection());
        } else if (playerCanMoveX) {
          player.moveHorizontal();
          playerGun.setTarget(playerGun.getTargetX() + player.getXDirection(), playerGun.getTargetY());
        } else if (playerCanMoveY) {
          player.moveVertical();
          playerGun.setTarget(playerGun.getTargetX(), playerGun.getTargetY() + player.getYDirection());
          
        }
        
        // Player shooting
        playerGun.reduceFireCooldown();
        
        if (player.isShooting()) {
          if (playerGun.shoot(currentRoom.getPlayerBullets())) {
            
            // Sound effect for different guns shooting
            if (playerGun instanceof BasicGun) {
              playSound(playerBasicGunShot);
            } else if (playerGun instanceof Shotgun) {
              playSound(playerShotgunShot);
            } else if (playerGun instanceof MachineGun) {
              playSound(playerMachineGunShot);
            } else if (playerGun instanceof ClearGun) {
              playSound(playerClearGunShot);
            } else {
              playSound(playerWaveGunShot);
            }
            
            // Update statistics
            if (playerGun instanceof Shotgun) {
              totalBulletsShot += 6;
            } else if ( (playerGun instanceof ClearGun) || (playerGun instanceof WaveGun) ) {
              totalBulletsShot += 20;
            } else {
              totalBulletsShot++;
            }
          }
        }

        player.reduceInvincibleTimer();
        
        /********************************************** Enemy Movement and Shooting **********************************************/
        
        if (boss == null) {
          
          for (int i = 0; i < enemies.size(); i++) {
            
            Enemy currentEnemy = enemies.get(i);
            Gun enemyGun = currentEnemy.getGun();
            
            double playerDistance = distance(player.getCenterX(), player.getCenterY(), currentEnemy.getCenterX(), currentEnemy.getCenterY());
            
            
            // Move the enemy if it is not within range of the player
            if (playerDistance >= currentEnemy.getMovementRange()) {
              
              int newEnemyX, newEnemyY;
              boolean enemyCanMove = true, enemyCanMoveX = true, enemyCanMoveY = true;
              Rectangle enemyMoved, enemyMovedX, enemyMovedY;
              
              /* Get the collision boxes of the enemy if it were to move
               * If any of these collision boxes intersect with a wall/player/another enemy, then the enemy will not in that direction
               */
              
              currentEnemy.calculateDirections(currentRoom, player);
              
              newEnemyX = currentEnemy.getX() + currentEnemy.getXDirection();
              newEnemyY = currentEnemy.getY() + currentEnemy.getYDirection();
              
              enemyMovedX = new Rectangle(newEnemyX,           currentEnemy.getY(), currentEnemy.getWidth(), currentEnemy.getHeight());
              enemyMovedY = new Rectangle(currentEnemy.getX(), newEnemyY,           currentEnemy.getWidth(), currentEnemy.getHeight());
              enemyMoved  = new Rectangle(newEnemyX,           newEnemyY,           currentEnemy.getWidth(), currentEnemy.getHeight());
              
              // Enemy-Player collision
              if (enemyMovedX.intersects(player.getCollisionBox())) {
                enemyCanMoveX = false;
              }
              if (enemyMovedY.intersects(player.getCollisionBox())) {
                enemyCanMoveY = false;
              }
              if (enemyMoved.intersects(player.getCollisionBox())) {
                enemyCanMove = false;
              }
              
              // Player takes damage if enemy walked into them
              if (! ( enemyCanMoveX && enemyCanMoveY && enemyCanMove ) ) {
                if (!player.isInvincible()) {
                  playSound(damageTaken);
                  player.changeHealth(-1);
                  player.setInvincibility(100);
                  totalDamageTaken++;
                }
              }
              
              // Enemy-Enemy collision 
              for (int j = 0; j < enemies.size(); j++) {
                if (i != j) {
                  Rectangle otherEnemy = enemies.get(j).getCollisionBox();
                  if (enemyMovedX.intersects(otherEnemy)) {
                    enemyCanMoveX = false;
                  }
                  if (enemyMovedY.intersects(otherEnemy)) {
                    enemyCanMoveY = false;
                  }
                  if (enemyMoved.intersects(otherEnemy)) {
                    enemyCanMove = false;
                  }
                }
              }
              
              // Enemy-Wall collision
              for (int j = 0; j < walls.size(); j++) {
                if (enemyMovedX.intersects(walls.get(j))) {
                  enemyCanMoveX = false;
                }
                if (enemyMovedY.intersects(walls.get(j))) {
                  enemyCanMoveY = false;
                }
                if (enemyMoved.intersects(walls.get(j))) {
                  enemyCanMove = false;
                }
              }
              
              // Move the enemy
              if (enemyCanMove) {
                currentEnemy.move();
              } else if (enemyCanMoveX && currentEnemy.getXDirection() != 0) {
                currentEnemy.moveHorizontal();
              } else if (enemyCanMoveY && currentEnemy.getYDirection() != 0) {
                currentEnemy.moveVertical();
              } else {
                currentEnemy.resetDestination();
              }
            }
            
            // Enemy shooting
            enemyGun.reduceFireCooldown();
            enemyGun.setTarget(player.getCenterX(), player.getCenterY());
            
            if (playerDistance <= currentEnemy.getRange()) {
              if (enemyGun.shoot(currentRoom.getEnemyBullets())) {
                
                // Sound effect for different guns shooting
                if (enemyGun instanceof BasicGun) {
                  playSound(enemyBasicGunShot);
                } else if (enemyGun instanceof Shotgun) {
                  playSound(enemyShotgunShot);
                } else if (enemyGun instanceof MachineGun) {
                  playSound(enemyMachineGunShot);
                } else if (enemyGun instanceof ClearGun) {
                  playSound(enemyClearGunShot);
                } else {
                  playSound(enemyWaveGunShot);
                }
              }
            }
          }
          
          /********************************************** Boss Movement and Shooting **********************************************/
          
        } else {
          
          boolean stillAttacking = true;
          
          // Boss moves to player
          if (bossAttackType == 0) {
            
            int newBossX, newBossY;
            boolean bossCanMove = true, bossCanMoveX = true, bossCanMoveY = true;
            Rectangle bossMoved, bossMovedX, bossMovedY;
            
            /* Get the collision boxes of the boss if it were to move
             * If any of these collision boxes intersect with the player, then the boss will not move in that direction
             */
            
            boss.calculateDirections();
            
            newBossX = boss.getX() + boss.getXDirection();
            newBossY = boss.getY() + boss.getYDirection();
            
            bossMovedX = new Rectangle(newBossX,    boss.getY(), boss.getWidth(), boss.getHeight());
            bossMovedY = new Rectangle(boss.getX(), newBossY,    boss.getWidth(), boss.getHeight());
            bossMoved  = new Rectangle(newBossX,    newBossY,    boss.getWidth(), boss.getHeight());
            
            if (bossMovedX.intersects(player.getCollisionBox())) {
              bossCanMoveX = false;
            }
            if (bossMovedY.intersects(player.getCollisionBox())) {
              bossCanMoveY = false;
            }
            if (bossMoved.intersects(player.getCollisionBox())) {
              bossCanMove = false;
            }
            
            // Player takes damage if boss moves into them
            if (! (bossCanMoveX && bossCanMoveY && bossCanMove) ) {
              if (!player.isInvincible()) {
                playSound(damageTaken);
                player.changeHealth(-1);
                player.setInvincibility(100);
                totalDamageTaken++;
              }
            }
            
            // Move the boss
            if (bossCanMove) {
              boss.move();
            } else if ( bossCanMoveX && (boss.getXDirection() != 0) ) {
              boss.moveHorizontal();
            } else if ( bossCanMoveY && (boss.getYDirection() != 0) ) {
              boss.moveVertical();
            }
            stillAttacking = bossAttackTimer < 90;
            boss.animateSprite();
            
          // Boss uses bullet attack
          } else {
            
            if (bossAttackTimer == 0) {
              switch (bossAttackType) {
                case 1:
                  playSound(bossAttack1);
                  break;
                case 2:
                  playSound(bossAttack2);
                  break;
                case 3:
                  playSound(bossAttack3);
                  break;
                case 4:
                  playSound(bossAttack4);
                  break;
                case 5:
                  playSound(bossAttack5);
                  break;
                case 6:
                  playSound(bossAttack6);
                  break;
              }
            }
            stillAttacking = boss.useAttack(bossAttackType, bossAttackTimer);
          }
          
          bossAttackTimer++;
          
          /* Choose next boss attack,
           * attacks alternate between moving towards player and a random bullet attack
           */
          
          if (!stillAttacking) {
            if (bossAttackType != 0) {
              bossAttackType = 0;
            } else {
              boss.setSprite(0);
              bossAttackType = random.nextInt(6)+1;
            }
            bossAttackTimer = 0;
          }
        }
        
        /********************************************** Player Bullets **********************************************/
        
        for (int i = playerBullets.size()-1; i >= 0; i--) {
          
          Projectile currentBullet = playerBullets.get(i);
          boolean hasCollided = false;
          
          // Bullet hits enemy
          if (boss == null) {
            
            for (int j = enemies.size()-1; j >= 0; j--) {
              Enemy currentEnemy = enemies.get(j);
              
              if (!hasCollided) {
                if (currentBullet.getCollisionBox().intersects(currentEnemy.getCollisionBox())) {
                  currentEnemy.changeHealth(-currentBullet.getDamage());
                  
                  // Delete enemy if it has no health
                  if (currentEnemy.getHealth() <= 0) {
                    playSound(enemyDeath);
                    enemies.remove(j);
                    totalEnemiesKilled++;
                  }
                  
                  hasCollided = true;
                  totalBulletsHit++;
                }
              }
            }
            
          // Bullet hits boss
          } else {
            if (currentBullet.getCollisionBox().intersects(boss.getCollisionBox())) {
              boss.changeHealth(-currentBullet.getDamage());
              hasCollided = true;
              
              totalBulletsHit++;
            }
          }
          
          // Bullet hits wall
          for (int j = 0; j < walls.size(); j++) {
            if (currentBullet.getCollisionBox().intersects(walls.get(j))) {
              hasCollided = true;
            }
          }
          
          // Bullet out of bounds          
          if ( (currentBullet.getX() < 0) || (currentBullet.getX() + currentBullet.getWidth() > xBoundary) ) {
            hasCollided = true;
          }
          if ( (currentBullet.getY() < 0) || (currentBullet.getY() + currentBullet.getHeight() > yBoundary) ) {
            hasCollided = true;
          }
          
          // Delete bullet if it has collided, otherwise move it
          if (hasCollided) {
            playerBullets.remove(i);
          } else {
            currentBullet.move();
          }
        }
        
        /********************************************** Enemy and Boss Bullets **********************************************/
        
        for (int i = enemyBullets.size()-1; i >= 0; i--) {
          
          Projectile currentBullet = enemyBullets.get(i);
          boolean hasCollided = false;
          
          // Bullet hits player
          if (currentBullet.getCollisionBox().intersects(player.getCollisionBox())) {
            if (!player.isInvincible()) {
              playSound(damageTaken);
              player.changeHealth(-1);
              player.setInvincibility(100);
              totalDamageTaken++;
            }
            hasCollided = true;
          }
          
          // Bullet hits wall
          for (int j = 0; j < walls.size(); j++) {
            if (currentBullet.getCollisionBox().intersects(walls.get(j))) {
              hasCollided = true;
            }
          }
          
          // Bullet out of bounds
          if ( (currentBullet.getX() < 0) || (currentBullet.getX() + currentBullet.getWidth() > xBoundary) ) {
            hasCollided = true;
          }
          if ( (currentBullet.getY() < 0) || (currentBullet.getY() + currentBullet.getHeight() > yBoundary) ) {
            hasCollided = true;
          }
          
          // Delete bullet if it has collided, otherwise move it
          if (hasCollided) {
            
            // This is a boss bullet that explodes upon impact
            if (currentBullet instanceof ExplosiveProjectile) {
              ((ExplosiveProjectile)currentBullet).explode(enemyBullets);
            }
            enemyBullets.remove(i);
          } else {
            currentBullet.move();
          }
        }
        
        /********************************************** Updating the Room **********************************************/
        
        
        // Open the room if it has been cleared
        if ( (currentRoom.getEnemies().size() == 0) && (boss == null) ) {
          if (!roomCleared[map.getCurrentRow()][map.getCurrentColumn()]) {
            map.openCurrentRoom();
            roomCleared[map.getCurrentRow()][map.getCurrentColumn()] = true;
            currentRoom.generateWalls();
            
            // Statistics
            if (!(map.getCurrentRoom().getItem() instanceof BossPortal)) {
              playSound(roomOpened);
              totalRoomsCleared++;
            }
          }
        }
        
        // Player moved into the room on the left of the current room
        if (player.getX() < 0) {
          
          map.getCurrentRoom().getPlayerBullets().clear();
          map.getCurrentRoom().getEnemyBullets().clear();
          map.changeCurrentRoom(map.getCurrentRow(), map.getCurrentColumn()-1);
          
          // Lock room and fill it with enemies if it has not been cleared
          if (!roomCleared[map.getCurrentRow()][map.getCurrentColumn()]) {
            playSound(roomLocked);
            map.lockCurrentRoom();
            map.getCurrentRoom().generateWalls();
            map.spawnEnemies(random.nextInt(4)+6);
            player.setInvincibility(100);
            player.setX(50*24-100);
          } else {
            player.setX(50*24-50);
          }
          
        }
        
        // Player moved into the room on the right of the current room
        if (player.getX() + player.getWidth() > xBoundary) {
          
          map.getCurrentRoom().getPlayerBullets().clear();
          map.getCurrentRoom().getEnemyBullets().clear();
          map.changeCurrentRoom(map.getCurrentRow(), map.getCurrentColumn()+1);
          player.setX(51);
          
          // Lock room and fill it with enemies if it has not been cleared
          if (!roomCleared[map.getCurrentRow()][map.getCurrentColumn()]) {
            playSound(roomLocked);
            map.lockCurrentRoom();
            map.getCurrentRoom().generateWalls();
            map.spawnEnemies(random.nextInt(4)+6);
            player.setInvincibility(100);
          }
        }
       
        // Player moved into the room above the current room
        if (player.getY() < 0) {
          
          map.getCurrentRoom().getPlayerBullets().clear();
          map.getCurrentRoom().getEnemyBullets().clear();
          map.changeCurrentRoom(map.getCurrentRow()-1, map.getCurrentColumn());
          
          // Lock room and fill it with enemies if it has not been cleared
          if (!roomCleared[map.getCurrentRow()][map.getCurrentColumn()]) {
            playSound(roomLocked);
            map.lockCurrentRoom();
            map.getCurrentRoom().generateWalls();
            map.spawnEnemies(random.nextInt(4)+6);
            player.setInvincibility(100);
            player.setY(50*24-100);
          } else {
            player.setY(50*24-50);
          }
        }
        
        // Player moved into the room below the current room
        if (player.getY() + player.getHeight() > yBoundary) {
          
          map.getCurrentRoom().getPlayerBullets().clear();
          map.getCurrentRoom().getEnemyBullets().clear();
          map.changeCurrentRoom(map.getCurrentRow()+1, map.getCurrentColumn());
          player.setY(51);
          
          // Lock room and fill it with enemies if it has not been cleared
          if (!roomCleared[map.getCurrentRow()][map.getCurrentColumn()]) {
            playSound(roomLocked);
            map.lockCurrentRoom();
            map.getCurrentRoom().generateWalls();
            map.spawnEnemies(random.nextInt(4)+6);
            player.setInvincibility(100);
          }
        }
        
        /********************************************** Game End Conditions **********************************************/
        
        // Defeat if player has no health
        if (player.getHealth() <= 0) {
          playSound(playerDeath);
          gameState = GameState.DEFEAT_SCREEN;
          totalDefeats++;
          
        // Victory if boss has no health
        } else if ( (boss != null) && (boss.getHealth() <= 0) ) {
          playSound(bossDeath);
          gameState = GameState.VICTORY_SCREEN;
          totalVictories++;
        }
        
        // Reset map, player, boss and sound
        if ( (gameState == GameState.VICTORY_SCREEN) || (gameState == GameState.DEFEAT_SCREEN) ) {
          if (gameMusic != null) {
            stopMusic(gameMusic);
          }
          if (bossMusic != null) {
            stopMusic(bossMusic);
            stopMusic(bossAttack1);
            stopMusic(bossAttack2);
            stopMusic(bossAttack3);
            stopMusic(bossAttack4);
            stopMusic(bossAttack5);
            stopMusic(bossAttack6);
          }
          player = null;
          boss = null;
          map = null;
        }
      }

      // Delay and update screen
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        System.out.println("Thread interrupted");
      }
      this.repaint();
    }    
  }
  /********************************* End of Animate *********************************/
  
  
  
  /**
   * GameAreaPanel
   * Represents a panel that can be drawn on
   */
  private class GameAreaPanel extends JPanel { 

    /**
     * GameAreaPanel constructor 
     */
    GameAreaPanel() {
      setBackground(Color.BLACK);
    }
    
    
    /**
     * paintComponent
     * Draws onto a graphics object
     * @param g The graphics object to draw on
     */
    public void paintComponent(Graphics g) {   
      
      super.paintComponent(g); 
      setDoubleBuffered(true); 
      g.setFont(font);
      
      switch (gameState) {
      
        case MENU:
          
          // Draw the different pages on the menu screen
          switch (menuState) {

            case DEFAULT:
              g.drawImage(mainMenuScreen,0,0,null);
              break;
              
            case STATISTICS:
              g.drawImage(statisticsScreen,0,0,null);
              g.setColor(new Color(255,255,255));
              g.drawString("total bullets shot:  " + String.format("%,d",totalBulletsShot) , 35, 246);
              g.drawString("total bullets hit:  " + String.format("%,d",totalBulletsHit) , 35, 246+75);
              g.drawString("total enemies killed:  " + String.format("%,d",totalEnemiesKilled) , 35, 246+75*2);
              g.drawString("total damage taken:  " + String.format("%,d",totalDamageTaken) , 35, 246+75*3);
              g.drawString("total rooms cleared:  " + String.format("%,d",totalRoomsCleared) , 35, 246+75*4);
              g.drawString("total victories:  " + String.format("%,d",totalVictories) , 35, 246+75*5);
              g.drawString("total defeats:  " + String.format("%,d",totalDefeats) , 35, 246+75*6);
              break;
              
            case HOW_TO_PLAY:
              g.drawImage(helpScreen,0,0,null);
              break;
          }
          
          break;
        
        case IN_GAME:

          // All objects in the game are shifted so that the player is always in the center of the frame
          int xShift = ( (WINDOW_WIDTH/2) - (player.getWidth()/2) ) - player.getX();
          int yShift = ( (WINDOW_HEIGHT/2) - (player.getHeight()/2) - 20) - player.getY();
          
          Gun playerGun = player.getGun();
          
          // Draw the room, which includes enemies, walls, and bullets
          if (map.getCurrentRoom() != null) {
            map.getCurrentRoom().draw(g, xShift, yShift);
          }
          
          // Rotate the player's gun to where the player is shooting
          if (playerGun instanceof BasicGun) {
            ((BasicGun) playerGun).calculateSprite();
          } else if (playerGun instanceof Shotgun) {
            ((Shotgun)playerGun).calculateSprite();
          } else if (playerGun instanceof MachineGun) {
            ((MachineGun)playerGun).calculateSprite();
          } else if (playerGun instanceof ClearGun) {
            ((ClearGun)playerGun).calculateSprite();
          } else if (playerGun instanceof WaveGun) {
            ((WaveGun)playerGun).calculateSprite();
          }
          
          // Draw the player and the player's gun
          if (player != null) {
            player.draw(g, xShift, yShift);
            playerGun.draw(g, xShift, yShift);
            if (player.isInvincible()) {
              g.drawImage(shieldImage, player.getX()-5+xShift, player.getY()-5+yShift, null);
            }
          }
          
          // Draw the boss
          if (boss != null) {
            boss.draw(g, xShift, yShift);
            
            // Draw boss health bar
            g.setColor(Color.WHITE);
            g.fillRect(95,695,630,60);
            g.setColor(Color.BLACK);
            g.fillRect(100,700,620,50);
            g.setColor(new Color(225,0,0));
            g.fillRect(100,700,(int)(620*(boss.getHealth()/3000.00)),50);
            g.setColor(new Color(200,0,0));
            g.fillRect(100,740,(int)(620*(boss.getHealth()/3000.00)),10);
          }
          
          // Draw hearts and keys for the player
          if (player != null) {
            for (int i = 0; i < player.getHealth(); i++) {
              g.drawImage(heartImage,30*((i)%20+1),30*((i)/20+1),null);
            }
            for (int i = 0; i < player.getKeys(); i++) {
              g.drawImage(keyImage,30+35*(i),30*((player.getHealth()-1)/20 + 2),null);
            }
          }
          
          // Draw the minimap
          if (map != null) {
            map.draw(g,675,30);
          }
          break;
         
        case DEFEAT_SCREEN:
          g.drawImage(defeatScreen,0,0,null);
          break;
          
        case VICTORY_SCREEN:
          g.drawImage(victoryScreen,0,0,null);
          break;
      }
    }
    /********************************* End of paintComponent *********************************/
  }
  /********************************* End of GameAreaPanel *********************************/
  
  
  
  /**
   * MyKeyListener
   * Detects keyboard input
   */
  private class MyKeyListener implements KeyListener {
    
    /**
     * keyPressed
     * This method is called when a key is pressed
     * @param e The key event when pressed
     */
    public void keyPressed(KeyEvent e) {
      
      if (gameState == GameState.IN_GAME) {
        
        // WASD keys for moving the player
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  
          player.setMovingRight(true);
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
          player.setMovingLeft(true);
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
          player.setMovingUp(true);
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
          player.setMovingDown(true);
          
        // E key to pick up items
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("E")) {
          
          Room currentRoom = map.getCurrentRoom();
          Item item = currentRoom.getItem();
          
          if (item != null) {
            
            // Check if player can reach the item
            if (item.inCollectRange(player.getCenterX(), player.getCenterY())) {
              
              // Increase the keys the player has
              if (item instanceof Key) {
                playSound(keyPickUp);
                player.changeKeys(1);
                currentRoom.setItem(null);
                
              // Swap the player's gun for the pick up gun
              } else if (item instanceof Gun) {
                Gun previousGun = player.getGun();
                player.setGun((Gun)item);
                playSound(gunPickUp);
                currentRoom.setItem(previousGun);
                currentRoom.getItem().setX(550);
                currentRoom.getItem().setY(550);
                
              // Heal the player
              } else if (item instanceof MedicKit) {
                playSound(healthPickUp);
                player.changeHealth(random.nextInt(2)+1);
                currentRoom.setItem(null);
                
              } else if (item instanceof BossPortal) {
                
                // Enter the boss room
                if ( ( (BossPortal) item).isOpen() ) {
                  
                  if (gameMusic != null) {
                    stopMusic(gameMusic);
                  }
                  if (bossMusic != null) {
                    playMusic(bossMusic);
                  }
                  
                  map.createBossRoom();
                  boss = new Boss (900,900,200,200,3000,3,player,map.getCurrentRoom());
                  bossAttackTimer = 0;
                  bossAttackType = 0;
                  player.setX(975);
                  player.setY(200);
                  
                // Use 3 keys to open the portal
                } else if (player.getKeys() >= 3) {
                  
                  playSound(unlockedBoss);
                  player.changeKeys(-3);
                  ((BossPortal) item).unlock();
                } 
              }
            }
          }
        }
      }
      
      // Exit the game
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        saveStatistics();
        System.exit(0);
      } 
    }
    
    /**
     * keyReleased
     * This method is called when a key is released
     * @param e The key event when released
     */
    public void keyReleased(KeyEvent e) {
      if (gameState == GameState.IN_GAME) {
        
        // Stop moving when releasing WASD
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
          player.setMovingRight(false);
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
          player.setMovingLeft(false);
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
          player.setMovingUp(false);
        } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
          player.setMovingDown(false);
        }
      }
    }
    
    /**
     * keyTyped
     * This method is called when a key is typed
     * @param e The key event when typed
     */
    public void keyTyped(KeyEvent e) {
    }
  } 
  /********************************* End of MyKeyListener *********************************/

  
  
  /**
   * MyMouseListener
   * Detects mouse input
   */
  private class MyMouseListener implements MouseListener {
    
    /**
     * mousePressed
     * This method is called when the mouse is pressed
     * @param e The mouse event when pressed
     */
    public void mousePressed(MouseEvent e) {
      
      int clickedX = e.getX() - 10;
      int clickedY = e.getY() - 30;
      
      switch (gameState) {
        
        case MENU:
          
          // Checking if certain buttons have been pressed in the menu
          switch (menuState) {

            case DEFAULT:
              
              // New game button
              if ( (clickedX>=43) && (clickedY>=271) && (clickedX<=311) && (clickedY<=326) ) {
              
                stopMusic(menuMusic);
                playMusic(gameMusic);
                playSound(clickSound);
                
                player = new Player (575,700,50,50,10,4);
                map = new Map (5, 5, 15);
                map.lockCurrentRoom();
                map.getCurrentRoom().generateWalls();
                gameState = GameState.IN_GAME;
                
              // Statistics button
              } else if ( (clickedX>=43) && (clickedY>=355) && (clickedX<=273) && (clickedY<=410) ) {
                playSound(clickSound);
                menuState = MenuState.STATISTICS;
                
              // Help button 
              } else if ( (clickedX>=43) && (clickedY>=438) && (clickedX<=346) && (clickedY<=502) ) {
                playSound(clickSound);
                menuState = MenuState.HOW_TO_PLAY;
                
              // Quit button
              } else if ( (clickedX>=43) && (clickedY>=523) && (clickedX<=149) && (clickedY<=587) ) {
                playSound(clickSound);
                saveStatistics();
                System.exit(0);
              }
              break;
              
            case STATISTICS:
              
              // Go back button
              if ( (clickedX>=23) && (clickedY>=19) && (clickedX<=86) && (clickedY<=63) ) {
                playSound(clickSound);
                menuState = MenuState.DEFAULT;
              }
              break;
              
            case HOW_TO_PLAY:
              
              // Go back button
              if ( (clickedX>=23) && (clickedY>=19) && (clickedX<=86) && (clickedY<=63) ) {
                playSound(clickSound);
                menuState = MenuState.DEFAULT;
              }
              break;
          }
          break;
        
        case IN_GAME:
          
          // Shoot where the mouse is, offset in relation to the player
          int xShift = ( (WINDOW_WIDTH/2) - (player.getWidth()/2) ) - player.getX();
          int yShift = ( (WINDOW_HEIGHT/2) - (player.getHeight()/2) - 20) - player.getY();
          player.setShooting(true);
          player.getGun().setTarget(clickedX - xShift, clickedY - yShift);
          break;
        
        // Defeat and victory screens have the same buttons
        case DEFEAT_SCREEN: 
        case VICTORY_SCREEN:
          
          // Return to menu button
          if ( (clickedX>=43) && (clickedY>=271) && (clickedX<=434) && (clickedY<=326) ) {
            if (menuMusic != null) {
              playMusic(menuMusic);
            }
            playSound(clickSound);
            gameState = GameState.MENU;
            
          // Quit button
          } else if ( (clickedX>=43) && (clickedY>=355) && (clickedX<=149) && (clickedY<=419) ) {
            playSound(clickSound);
            saveStatistics();
            System.exit(0);
          }
          break;
      }
    }
    
    /**
     * mouseReleased
     * This method is called when the mouse is released
     * @param e The mouse event when released
     */
    public void mouseReleased(MouseEvent e) {
      
      // Stop shooting when released
      if (gameState == GameState.IN_GAME) {
        player.setShooting(false);
      }
    }
    
    /**
     * mouseClicked
     * This method is called when the mouse is clicked
     * @param e The mouse event when clicked
     */
    public void mouseClicked(MouseEvent e) {
    }
    
    /**
     * mouseEntered
     * This method is called when the mouse has entered
     * @param e The mouse event when entered
     */
    public void mouseEntered(MouseEvent e) {
    }
    
    /**
     * mouseExited
     * This method is called when the mouse has exited
     * @param e The mouse event when exited
     */
    public void mouseExited(MouseEvent e) {
    }
  }
  /********************************* End of MyMouseListener *********************************/
  
  
  
  /**
   * MyMouseMotionListener
   * Detects mouse movement
   */
  private class MyMouseMotionListener implements MouseMotionListener {
    
    /**
     * mouseDragged
     * This method is called when the mouse is dragged
     * @param e The mouse event when dragged
     */
    public void mouseDragged(MouseEvent e){
      if (gameState == GameState.IN_GAME) {

        // Move the player's shooting target as the mouse moves
        int xShift = ( (WINDOW_WIDTH/2) - (player.getWidth()/2) ) - player.getX();
        int yShift = ( (WINDOW_HEIGHT/2) - (player.getHeight()/2) - 20) - player.getY();
        player.getGun().setTarget(e.getX() - xShift - 10, e.getY() - yShift - 30); 
      }
    }
    
    /**
     * mouseMoved
     * This method is called when the mouse is moved
     * @param e The mouse event when moved
     */
    public void mouseMoved(MouseEvent e) {
    }
  }
  /********************************* End of MyMouseMotionListener *********************************/
  
  
  
  /**
   * MyWindowListener
   * Detects changes in the window
   */
  private class MyWindowListener implements WindowListener {
    
    /**
     * windowClosing
     * This method is called when the window is closing
     * @param e The window event when closing
     */
    public void windowClosing(WindowEvent e) {
      saveStatistics();
      System.exit(0);
    }
    
    /**
     * windowDeactivated
     * This method is called when the window has been deactivated
     * @param e The window event when deactivated
     */
    public void windowDeactivated(WindowEvent e) {
      
      // Make sure the player isn't moving if the window isn't in focus
      if (player != null) {
        player.setMovingRight(false);
        player.setMovingLeft(false);
        player.setMovingUp(false);
        player.setMovingDown(false);
      }
    }
    
    /**
     * windowActivated
     * This method is called when the window has been activated
     * @param e The window event when activated
     */
    public void windowActivated(WindowEvent e) {
    }
    
    /**
     * windowClosed
     * This method is called when the window has been closed
     * @param e The window event when closed
     */
    public void windowClosed(WindowEvent e) {
    }
    
    /**
     * windowDeiconified
     * This method is called when the window has been deiconified
     * @param e The window event when deiconified
     */
    public void windowDeiconified(WindowEvent e) {
    }
    
    /**
     * windowIconified
     * This method is called when the window has been iconified
     * @param e The window event when iconified
     */
    public void windowIconified(WindowEvent e) {
    }
    
    /**
     * windowOpened
     * This method is called when the window has been opened
     * @param e The window event when opened
     */
    public void windowOpened(WindowEvent e) {
    }
  }
  /********************************* End of MyWindowListener *********************************/
    
  
  
  /**
   * distance
   * This method finds the distance between two points
   * @param x1 The x value of the first point
   * @param y1 The y value of the first point
   * @param x2 The x value of the second point
   * @param y2 The y value of the second point
   * @return The distance
   */
  public static double distance(double x1, double y1, double x2, double y2) {
    return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
  }
  
  /**
   * playSound
   * This method plays a sound clip from the beginning
   * @param sound The clip to be played
   * @return If the clip is valid
   */
  public static boolean playSound(Clip sound) {
    if (sound == null) {
      return false;
    }
    if (sound.isRunning()){
      sound.stop();
    }
    sound.flush();
    sound.setFramePosition(0);
    sound.start();
    return true;
  }
  
  /**
   * playMusic
   * This method plays a music clip continuously
   * @param music The clip to be played
   * @return If the clip is valid
   */
  public static boolean playMusic(Clip music) {
    if (music == null) {
      return false;
    }
    music.start();
    music.loop(Clip.LOOP_CONTINUOUSLY);
    return true;
  }
  
  /**
   * stopMusic
   * This method stops a music clip and resets it
   * @param music The clip to be stopped
   * @return If the clip is valid
   */
  public static boolean stopMusic(Clip music) {
    if (music == null) {
      return false;
    }
    if (music.isRunning()) {
      music.stop();
    }
    music.flush();
    music.setFramePosition(0);
    return true;
  }
  
  /**
   * saveStatistics
   * This method writes all the statistics to a file
   */
  public void saveStatistics() {
    String statistics = "";
    statistics += totalBulletsShot + "\n";
    statistics += totalBulletsHit + "\n";
    statistics += totalEnemiesKilled + "\n";
    statistics += totalDamageTaken + "\n";
    statistics += totalRoomsCleared + "\n";
    statistics += totalVictories + "\n";
    statistics += totalDefeats + "\n";
    writeToFile("statistics.txt", statistics);
  }
  
  /**
   * writeToFile
   * This method writes to a file
   * @param fileName The file to write to
   * @param content What to write on the file
   */
  public static void writeToFile(String fileName, String content) {
    try {
      PrintWriter fileOutput = new PrintWriter(new File(fileName));
      fileOutput.print(content);
      fileOutput.close();
    } catch (FileNotFoundException e) {
      System.out.println("No file by the name of " + fileName + " exists.");
    }
  }
  
  /**
   * loadSprite
   * Reads and returns an image
   * @param fileName The image to read
   * @return A BufferedImage representing the image of the specified file or null if there were errors reading the file
   */
  public static BufferedImage loadSprite(String fileName) {
    try {
      return (ImageIO.read(new File(fileName)));
    } catch (FileNotFoundException e) {
      System.out.println("No file by the name '" + fileName + "' exists.");
    } catch (IOException e) {
      System.out.println("Error loading the file '" + fileName + "'.");
    }
    return null;
  }
  
  /**
   * loadAudio
   * Reads and returns a clip of an audio file
   * @param fileName The audio file to read
   * @return A Clip representing the audio file or null if there were errors reading the file
   */
  public static Clip loadAudio(String fileName) {
    try {
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(new File(fileName)));
      return clip;
    } catch (FileNotFoundException e) {
      System.out.println("Missing audio file by the name of '" + fileName + "'.");
    } catch (UnsupportedAudioFileException e) {
      System.out.println("The audio file '" + fileName + "' is unsupported");
    } catch (LineUnavailableException e) {
      System.out.println("Error loading '" + fileName + "'");
    } catch (IOException e) {
      System.out.println("Error loading '" + fileName + "'");
    }
    return null;
  }
}
