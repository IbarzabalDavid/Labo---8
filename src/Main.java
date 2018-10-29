import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
//VARIABLES
        Image[][] tab2D=
                {
                        {
                                new Image("image/sim0.jpg")
                                ,new Image("image/sim1.jpg")
                                ,new Image("image/sim2.jpg")
                                ,new Image("image/sim3.jpg")
                                ,new Image("image/sim4.jpg")
                                ,new Image("image/sim5.jpg")
                                ,new Image("image/sim6.jpg")
                                ,new Image("image/sim7.jpg")
                                ,new Image("image/sim8.jpg")
                        },
                        {
                                new Image("image/raph0.jpg")
                                ,new Image("image/raph1.jpg")
                                ,new Image("image/raph2.jpg")
                                ,new Image("image/raph3.jpg")
                                ,new Image("image/raph4.jpg")
                                ,new Image("image/raph5.jpg")
                                ,new Image("image/raph6.jpg")
                                ,new Image("image/raph7.jpg")
                                ,new Image("image/raph8.jpg")
                        },
                        {
                                new Image("image/carl0.jpg")
                                ,new Image("image/carl1.jpg")
                                ,new Image("image/carl2.jpg")
                                ,new Image("image/carl3.jpg")
                                ,new Image("image/carl4.jpg")
                                ,new Image("image/carl5.jpg")
                                ,new Image("image/carl6.jpg")
                                ,new Image("image/carl7.jpg")
                                ,new Image("image/carl8.jpg")
                        },
                        {
                                new Image("image/nic0.jpg")
                                ,new Image("image/nic1.jpg")
                                ,new Image("image/nic2.jpg")
                                ,new Image("image/nic3.jpg")
                                ,new Image("image/nic4.jpg")
                                ,new Image("image/nic5.jpg")
                                ,new Image("image/nic6.jpg")
                                ,new Image("image/nic7.jpg")
                                ,new Image("image/nic8.jpg")
                        },
                        {
                                new Image("image/mario0.jpg")
                                ,new Image("image/mario1.jpg")
                                ,new Image("image/mario2.jpg")
                                ,new Image("image/mario3.jpg")
                                ,new Image("image/mario4.jpg")
                                ,new Image("image/mario5.jpg")
                                ,new Image("image/mario6.jpg")
                                ,new Image("image/mario7.jpg")
                                ,new Image("image/mario8.jpg")
                        }
                };
        ArrayList<Image> ima=new ArrayList<>();
        for (int i=0;i<9;i++){
            ima.add(tab2D[4][i]);
        }
        Collections.shuffle(ima);
        ImageView[] views=new ImageView[9];
        for (int i=0;i<9;i++){
            views[i]=new ImageView(ima.get(i));
            views[i].setRotate((int)(Math.random() * 4) * 90);
        }
        Menu impo = new Menu("Importer");
        Menu action = new Menu("Action");
        MenuItem mix = new MenuItem("Mélanger");
        MenuItem[] menuItems={
                new MenuItem("Simon")
                ,new MenuItem("Raphael")
                ,new MenuItem("Charles")
                ,new MenuItem("Nicolas")
                , new MenuItem("Mario")
        };

        impo.getItems().addAll(menuItems[0], menuItems[1],menuItems[2],menuItems[3],menuItems[4]);
        action.getItems().addAll(mix);
        MenuBar menuBar = new MenuBar(impo, action);
        int pts=0;
        final int[] tabPts={pts};
        int liveTab=4;
        final int[] liveTablo={liveTab};
        Text txt=new Text("Nb de pts : "+tabPts[0]);
        HBox hb1 = new HBox(views[0], views[1], views[2]);
        HBox hb2 = new HBox(views[3], views[4], views[5]);
        HBox hb3 = new HBox(views[6], views[7], views[8]);
        primaryStage.setWidth(900);
        primaryStage.setHeight(900);
        primaryStage.setTitle("Casse-tëte");
        hb1.setAlignment(Pos.CENTER);
        hb1.setSpacing(2);
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(2);
        hb3.setAlignment(Pos.CENTER);
        hb3.setSpacing(2);
        VBox vb = new VBox(hb1, hb2, hb3);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(2);
        BorderPane section = new BorderPane();
        section.setTop(menuBar);
        section.setCenter(vb);
        section.setBottom(txt);
        Scene sc1 = new Scene(section);
        primaryStage.setScene(sc1);
        primaryStage.show();
//ACTION
        //melanger
        mix.setOnAction((event) -> {
            Collections.shuffle(ima);
            for (int i=0;i<views.length;i++){
                views[i].setImage(ima.get(i));
                views[i].setRotate((int)(Math.random() * 4) * 90);
            }
        });
        sc1.setOnKeyPressed(event->{
            if(event.isControlDown() && event.getCode() == KeyCode.M) {
                mix.fire();
            }
        });
        //loadPhoto
        for (int i=0;i<5;i++){
            final int num=i;
            menuItems[i].setOnAction((event) -> {
                load(tab2D[num],ima,views);
                liveTablo[0]=num;
            });
        }
        //dragNdrop
        for (int i=0;i<9;i++){
            final int number=i;
            views[i].setOnDragDetected(event -> {
                Dragboard dragboard = views[number].startDragAndDrop(TransferMode.MOVE);
                ClipboardContent contenu = new ClipboardContent();
                contenu.putImage(views[number].getImage());
                dragboard.setContent(contenu);
            } );
            views[i].setOnDragDone(event -> {
                if (chekUp(views,tab2D,liveTablo)){
                    mix.fire();
                    tabPts[0]++;
                    txt.setText("Nb de pts : "+tabPts[0]);
                }

            });
            views[i].setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.MOVE);
            });
            views[i].setOnDragDropped(event -> {
                Image temp=((ImageView)event.getGestureSource()).getImage();
                double tempo=((ImageView)event.getGestureSource()).getRotate();
                ((ImageView)event.getGestureSource()).setImage(views[number].getImage());
                views[number].setImage(temp);
                ((ImageView)event.getGestureSource()).setRotate(views[number].getRotate());
                views[number].setRotate(tempo);

            });
            views[i].setOnMouseClicked(event1 -> {
                views[number].setRotate(views[number].getRotate()+90);
                if (chekUp(views,tab2D,liveTablo)){
                    mix.fire();
                    tabPts[0]++;
                    txt.setText("Nb de pts : "+tabPts[0]);
                }
            });
        }
    }
    public void load(Image[] tab, ArrayList<Image> ima, ImageView[] views){
        ima.clear();
        for (int i=0;i<9;i++){
            ima.add(tab[i]);
        }
        Collections.shuffle(ima);
        for (int i=0;i<9;i++){
            views[i].setImage(ima.get(i));
        }

    }
    public boolean chekUp(ImageView[] views, Image[][] tab2D, int[]liveTablo){
        boolean done=true;
        for (int j=0; j<9; j++){
            if(!views[j].getImage().equals(tab2D[liveTablo[0]][j]) || views[j].getRotate()%360!=0){
                done=false;
            }
        }
        if (done){
            Label win = new Label("BRAVO!!!");
            Label replay = new Label("Vous avez résolu le casse-tête. Voulez-vous rejouer?");
            VBox vBox = new VBox(win, replay);
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(vBox);
            dialog.setTitle("Félicitation");
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("Rejouer"));
            dialog.showAndWait();
        }
        return done;
    }

}