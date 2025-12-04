package com.Edstrom;

import com.Edstrom.dataBase.MemberRegistry;
import com.Edstrom.entity.Member;
import com.Edstrom.entity.StatusLevel;
import com.Edstrom.service.MembershipService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.Arrays;

public class Main extends Application {


    MemberRegistry memberRegistry = new MemberRegistry();
    MembershipService membershipService = new MembershipService(memberRegistry);
    //fillMemberList(membershipService);

    TableView<Member> memberTable;
    TextField idInput, nameInput, statusLevelInput;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle(" Welcome to membershipclub");

        //MemberTable
        memberTable = new TableView();
        memberTable.setEditable(true);      //För att ändra namn på en person ??


        //columns
        //Id
        TableColumn<Member, Integer> idColumn = new TableColumn<>("Id number");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //För att ändra id
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumn.setOnEditCommit(event -> {
        Member idChangedMember = event.getRowValue();
        idChangedMember.setId(event.getNewValue());
        memberTable.refresh();
        });
        //Name
        TableColumn<Member, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setMinWidth(150);    //newBoston körde 200
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // För att ändra namn
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        nameColumn.setOnEditCommit(event ->{
        Member changedMember = event.getRowValue();
        changedMember.setName(event.getNewValue());
        memberTable.refresh();
        });

        nameColumn.setEditable(true);   //Hit ner yoyo
        //StatusLevel
        TableColumn<Member, String> statusLevelColumn = new TableColumn<>("Statuslevel");
        statusLevelColumn.setMinWidth(100);
        statusLevelColumn.setCellValueFactory(new PropertyValueFactory<>("statusLevel"));
        // Choices from enum
        ObservableList<String> statusChoice = FXCollections.observableArrayList(
                Arrays.stream(StatusLevel.values()).map(StatusLevel::toString).toArray(String[]::new)
        );

        statusLevelColumn.setEditable(true);
        // ComboBox for enums
        statusLevelColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<String>(){
        @Override
        public String toString(String object){return object;}
        @Override
        public String fromString(String string) {return string;}
        }, statusChoice.toArray(new String[0])));
        // Write string back to member
        statusLevelColumn.setOnEditCommit(memberStringCellEditEvent -> {
            Member newStatusMember = memberStringCellEditEvent.getRowValue();
            String newValue = memberStringCellEditEvent.getNewValue();
            // Här kan vi utveckla nå fint Exception
            if (newValue != null) {
                newStatusMember.setStatusLevel(newValue);
            }
        });

        //add to tableView
        memberTable.setItems(membershipService.getMembers());
        memberTable.getColumns().addAll(idColumn, nameColumn, statusLevelColumn);


        //Inputs textfields
        //Id
        idInput = new TextField();
        idInput.setPromptText("Id");
        idInput.setMinWidth(60);
        //Name
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);     //Behövs bara i första så följer resten bredden.
        //StatusLevel
        statusLevelInput = new TextField();
        statusLevelInput.setPromptText("Statuslevel");

        //Buttons
        //Add member
        Button addButton = new Button("Add member");
        addButton.setOnAction(event -> addButtonClicked());

        //Delete member
        Button deleteButton = new Button("Remove member");
        deleteButton.setOnAction(event -> deleteButtonClicked());


        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, nameInput, statusLevelInput, addButton, deleteButton);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(memberTable, hBox);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
        // TODO METODER
    public void addButtonClicked() {
        membershipService.addMember(
                Integer.parseInt(idInput.getText()),  // Här ska det in nå fina Exceptions osså
        nameInput.getText(),
        statusLevelInput.getText());
        idInput.clear();
        nameInput.clear();
        statusLevelInput.clear();
    }
    public void deleteButtonClicked() {
        ObservableList<Member> memberSelected, allMembers;
        allMembers = memberTable.getItems();
        memberSelected = memberTable.getSelectionModel().getSelectedItems();
        memberSelected.forEach(allMembers::remove);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ObservableList<Member> getMember(){
        ObservableList<Member> members = FXCollections.observableArrayList();

        members.add(new Member(1,"FiaStina", "Student"));
        members.add(new Member(2,"LisaLasse", "Student"));
        members.add(new Member(3,"GulliGunnar", "Standard"));
        members.add(new Member(4,"StekarJanne", "Standard"));
        members.add(new Member(5,"ClaustHauler", "Premium"));
        return members;
    }



}



/*
private static void fillMemberList(MembershipService membershipService) {
    membershipService.registerMember("Ofelia", "standard");
    membershipService.registerMember("Lisa", "premium");
    membershipService.registerMember("Thor", "student");
    membershipService.registerMember("KentJesus", "student");
    membershipService.registerMember("Majken", "standard");
}

    */