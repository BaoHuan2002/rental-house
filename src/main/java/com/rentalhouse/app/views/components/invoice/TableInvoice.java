package com.rentalhouse.app.views.components.invoice;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.rentalhouse.app.controllers.InfrastructureController;
import com.rentalhouse.app.controllers.InvoiceController;
import com.rentalhouse.app.controllers.NoticesController;
import com.rentalhouse.app.controllers.TelegramSender;
import com.rentalhouse.app.controllers.UserController;
import com.rentalhouse.app.middlewares.ValidateInput;
import com.rentalhouse.app.models.Invoice;
import com.rentalhouse.app.models.Notice;
import com.rentalhouse.app.views.components.AlertPopup.AlertPopup;
import com.rentalhouse.app.views.containers.Dashboard.InfrastructureDetail.InfrastructureDetailScene;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Callback;

public class TableInvoice implements Initializable {
   @FXML private Label tableInvoice_electricPriceTotal;
   @FXML private Label tableInvoice_infrastructureID;
   @FXML private Label tableInvoice_listedPrice;
   @FXML private TextField tableInvoice_newElectric;
   @FXML private TextField tableInvoice_newWater;
   @FXML private TextField tableInvoice_oldElectric;
   @FXML private TextField tableInvoice_oldWater;
   @FXML private TableView<Invoice> tableInvoice_tableViews;
   @FXML private Label tableInvoice_totalPrice;
   @FXML private Button tableInvoice_confirmPayment;
   @FXML private Button tableInvoice_updateShowPDF;
   @FXML private Button tableInvoice_updateBTN;
   @FXML private Button close;
   @FXML private Button minimize;
   @FXML private Button restore;
   @FXML private Button minus;
   @FXML private Label tableInvoice_waterPriceTotal;
   @FXML private StackPane main_form;
   @FXML private AnchorPane tableInvoice_container;
   @FXML private TableColumn<Invoice, String> tableInvoice_columnIDInvoice;
   @FXML private TableColumn<Invoice, Date> tableInvoice_column_date;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_electricPrice;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_listedPrice;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_newElectric;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_newWater;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_oldElectric;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_oldWater;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_totalPrice;
   @FXML private TableColumn<Invoice, String> tableInvoice_column_waterPrice;
   @FXML private TableColumn<Invoice, Integer> tableInvoice_column_statusPayment;
   @FXML private TableColumn<Invoice, Void> tableInvoice_column_checkPaid;
   @FXML private TableColumn<Invoice, Integer> tableInvoice_column_noticesStatus;
   @FXML private Label tableInvoice_EPrice;
   @FXML private Label tableInvoice_WPrice;
   @FXML private Label notice_labelExplain;
   @FXML private AnchorPane invoiceTable_titleBar;
   @FXML private ComboBox<String> tableInvoice_selectMonth;
   @FXML private ComboBox<String> tableInvoice_selectYear;
   @FXML private ComboBox<String> tableInvoice_selectPriceTotal;
   @FXML private TextField tableInvoice_search;
   @FXML private Label tableInvoice_alertReload;
   @FXML private Label tableInvoice_electricityUsed;
   @FXML private Label tableInvoice_waterUsed;
   @FXML private Label tableInvoice_showDate;
   @FXML private AnchorPane tableInvoice_alertPayment;
   @FXML private RadioButton tableInvoice_paidRadio;
   @FXML private Button tableInvoice_createNotice;
   @FXML private FontAwesomeIcon tableInvoice_bellAlertPayment;
   @FXML private AnchorPane tableInvoice_noticeForm;
   @FXML private AnchorPane tableInvoice_tableForm;
   @FXML private Button noticeForm_backBTN;
   @FXML private RadioButton notice_extend_radio;
   @FXML private GridPane notice_gridPaneNotices;
   @FXML private RadioButton notice_others_radio;
   @FXML private RadioButton notice_prePayment_radio;
   @FXML private TextField notice_receivedAmount;
   @FXML private TextArea notice_description;
   @FXML private TextField notice_remainingAmount;
   @FXML private Label notice_totalPrice;
   @FXML private Label notice_invoiceID;
   @FXML private ToggleGroup caseNotices;
   @FXML private AnchorPane tableInvoice_header;
   @FXML private AnchorPane tableInvoice_sideBar;
   @FXML private Label notice_countChars;
   @FXML private Label notice_strLength;
   @FXML TextField notice_searchCase;
   @FXML private Label notice_processed;
   @FXML private Label notice_total;
   @FXML private Label notice_unprocessed;
   @FXML private Label notice_totalNotice;
   @FXML private Button notice_createNotice;

   private InvoiceController invoiceController;
   public static String infrastructureID = null;
   public static String infrastructureName = null;
   public static BigDecimal electricPrice = null;
   public static BigDecimal waterPrice = null;
   private Invoice invoiceUpdate;
   private Boolean check = false;
   public static LocalDate checkCurrentDate;
   private InfrastructureController infrastructureController;
   private final NoticesController noticesController;
   private Boolean isChecked = false;
   private int col = 0;
   private int row = 1;
   private List<RadioButton> radioButton;
   public static Button deleteNotice;
   private String invoiceID = null;
   public static RadioButton checkDone;
   public static AnchorPane noticePane;
   public static TableRow<Invoice> tableRow;

   public TableInvoice() {
      this.invoiceController = new InvoiceController();
      this.infrastructureController = new InfrastructureController();
      this.noticesController = new NoticesController();
   }
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      // if (invoiceController.getInvoice(infrastructureID) != null) {
      //    if (invoiceController.getInvoice(infrastructureID).getStatus() == 1) {
      //       tableInvoice_alertPayment.setDisable(true);
      //    } else {
      //       tableInvoice_alertPayment.setDisable(false);
      //    }
      // }
      // tableInvoice_sideBar.setDisable(true);
      tableInvoice_alertPayment.setDisable(true);
      tableInvoice_oldWater.setDisable(true);
      tableInvoice_oldElectric.setDisable(true);
      tableInvoice_confirmPayment.setDisable(true);
      tableInvoice_updateShowPDF.setDisable(true);
      restore.setVisible(false);
      minimize.setVisible(true);
      tableInvoice_infrastructureID.setText(infrastructureName);
      showDataOnTable();
      showMonthComboBox(tableInvoice_selectMonth);
      showYearComboBox(tableInvoice_selectYear);
      filterMonth(tableInvoice_selectMonth);
      filterYear(tableInvoice_selectYear);
      filterPrice(tableInvoice_selectPriceTotal);
      showTooltip();
      PauseTransition pause = new PauseTransition();
      pause.setOnFinished(e -> searchOnTable());
      tableInvoice_search.textProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            Platform.runLater(() -> {
               pause.playFromStart();
            });
         }
      });
      setDisableIntoTable(true);
   }

   public void setInvoice(Invoice invoice) {
      this.invoiceUpdate = invoice;
   }
   public Invoice getInvoice() {
      return invoiceUpdate;
   }
   public void reloadTable(ActionEvent e) {
      setInvoice(null);
      tableInvoice_newElectric.setDisable(true);
      tableInvoice_newWater.setDisable(true);
      tableInvoice_updateBTN.setDisable(true);
      tableInvoice_confirmPayment.setDisable(true);
      isChecked = false;
      tableInvoice_createNotice.setVisible(false);
      tableInvoice_tableViews.setItems(FXCollections.observableList(invoiceController.getAll()
         .stream()
         .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
         .collect(Collectors.toList())));
         resetInfo();
         tableInvoice_alertReload.setText("(*) Reload Table Completed !");
      new Thread(() -> {
         try {
            Thread.sleep(2000);
         } catch (Exception ex) {
            ex.printStackTrace();
         }
         Platform.runLater(() -> {
            tableInvoice_alertReload.setText("");
         });
      }).start();
   }
   public void reloadTableRow() {
      tableInvoice_createNotice.setVisible(false);
      tableInvoice_tableViews.setItems(FXCollections.observableList(invoiceController.getAll()
      .stream()
      .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
      .collect(Collectors.toList())));
      tableInvoice_createNotice.setVisible(true);
   }
   public void close() {
      close.getScene().getWindow().hide();
   }
   public void minus() {
      Stage mainTableInvoice = (Stage) main_form.getScene().getWindow();
      mainTableInvoice.setIconified(true);
   }
   public void minimizeRestore(ActionEvent event) {
      Stage mainTableInvoice = (Stage) main_form.getScene().getWindow();
      if (event.getSource() == minimize) {
         mainTableInvoice.setMaximized(true);
         minimize.setVisible(false);
         restore.setVisible(true);
      } else if (event.getSource() == restore) {
         mainTableInvoice.setMaximized(false);
         minimize.setVisible(true);
         restore.setVisible(false);
      }
   }
   public void doubleClickTitleBar(MouseEvent event) {
      Stage mainTableInvoice = (Stage) main_form.getScene().getWindow();
      if (event.getClickCount() == 2 && !check) {
         mainTableInvoice.setMaximized(true);
         minimize.setVisible(false);
         restore.setVisible(true);
         check = true;
         return;
      }else if (check && event.getClickCount() == 2) {
         mainTableInvoice.setMaximized(false);
         minimize.setVisible(true);
         restore.setVisible(false);
         check = false;
         return;
      }
   }
   // todo SET & SHOW ALL DATA ON TABLE VIEW:
   public void showDataOnTable() {
      // GET NAME ATTRIBUTES WITHIN MODEL INVOICE TO MAPPING FOR EACH COLUMN WITHIN TABLE VIEW:
      tableInvoice_columnIDInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiveID"));
      tableInvoice_column_listedPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
      tableInvoice_column_waterPrice.setCellValueFactory(new PropertyValueFactory<>("water_price"));
      tableInvoice_column_electricPrice.setCellValueFactory(new PropertyValueFactory<>("electricity_price"));
      tableInvoice_column_oldElectric.setCellValueFactory(new PropertyValueFactory<>("OldElectricity_number"));
      tableInvoice_column_newElectric.setCellValueFactory(new PropertyValueFactory<>("NewElectricity_number"));
      tableInvoice_column_oldWater.setCellValueFactory(new PropertyValueFactory<>("OldWater_number"));
      tableInvoice_column_newWater.setCellValueFactory(new PropertyValueFactory<>("NewWater_number"));
      tableInvoice_column_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
      tableInvoice_column_date.setCellValueFactory(new PropertyValueFactory<>("create_at"));
      tableInvoice_column_statusPayment.setCellValueFactory(new PropertyValueFactory<>("status"));
      tableInvoice_EPrice.setText("$" + electricPrice.toString() + " /kw/h");
      tableInvoice_WPrice.setText("$" + waterPrice.toString() + " /m3");

      tableInvoice_tableViews.setItems(FXCollections.observableList(invoiceController.getAll()
      .stream()
      .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
      .collect(Collectors.toList())));

      tableInvoice_tableViews.getColumns().stream().forEach((column) -> {
         Text t = new Text(column.getText());
         double max = t.getLayoutBounds().getWidth();
         for (int i = 0; i < tableInvoice_tableViews.getItems().size(); i++) {
               if (column.getCellData(i) != null) {
                  t = new Text(column.getCellData(i).toString());
                  double width = t.getLayoutBounds().getWidth();
                  if (width > max) {
                     max = width;
                  }
               }
         }
         column.setPrefWidth(max + 20.0d); // Padding
      });
      customizeCellsDate();
      customCellOld(tableInvoice_column_oldElectric);
      customCellOld(tableInvoice_column_oldWater);
      customCellNew(tableInvoice_column_newElectric);
      customCellNew(tableInvoice_column_newWater);
      customPrice(tableInvoice_column_electricPrice);
      customPrice(tableInvoice_column_waterPrice);
      customPrice(tableInvoice_column_totalPrice);
      customListedPrice(tableInvoice_column_listedPrice);
      customInvoiceID(tableInvoice_columnIDInvoice);
      customCellStatus(tableInvoice_column_statusPayment);
      addRadioButtonColumnCheckPaid(tableInvoice_column_checkPaid);
      addCountNoticeStatus(tableInvoice_column_noticesStatus);
   }
   private void customizeCellsDate() {
      tableInvoice_column_date.setCellFactory(col -> new TableCell<Invoice, Date>() {
            @Override
            public void updateItem(Date item, boolean empty) {
               super.updateItem(item, empty);
               if (item == null || empty) {
                  setText(null);
               } else {
                  setText(item.toString());
               }
               setStyle(
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 11px;" +
                        "-fx-text-fill: #0070c6;" +
                        "-fx-padding: 5 5 5 5;" 
               );
            }
      });
   }
   private void customCellOld(TableColumn<Invoice, String> oldValue) {
      oldValue.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #0F176F;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }
   private void customCellNew(TableColumn<Invoice, String> newValue) {
      newValue.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #279a00;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }

   public void customCellStatus(TableColumn<Invoice, Integer> status) {
      tableInvoice_column_statusPayment.setCellFactory(col -> new TableCell<Invoice, Integer>() {
         @Override
         public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
               setStyle(""); // Reset style when cell is empty
            } else {
               if (item == 0) {
                     setText("Unpaid");
                     setStyle(
                        "-fx-font-weight: 700;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: #ff0000;" +
                        "-fx-padding: 5 5 5 5;"
                     );
               } else {
                     setText("Paid");
                     setStyle(
                        "-fx-font-weight: 700;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: #279a00;" +
                        "-fx-padding: 5 5 5 5;"
                     );
               }
            }
         }
      });
   }
   private void customPrice(TableColumn<Invoice, String> price) {
      price.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #bc0000;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }
   private void customListedPrice(TableColumn<Invoice, String> price) {
      price.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 13px;" +
                     "-fx-text-fill: #ff5500;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }
   private void customInvoiceID(TableColumn<Invoice, String> ID) {
      ID.setCellFactory(col -> new TableCell<Invoice, String>() {
         @Override
         public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
               setText(null);
            } else {
               setText(item.toString());
            }
            setStyle(
                     "-fx-font-weight: 700;" +
                     "-fx-font-size: 11px;" +
                     "-fx-padding: 5 5 5 5;"
            );
         }
      });
   }

   public void addCountNoticeStatus(TableColumn<Invoice, Integer> noticesStatus) {
      tableInvoice_column_noticesStatus.setCellFactory(new Callback<TableColumn<Invoice, Integer>, TableCell<Invoice, Integer>>() {
         @Override
         public TableCell<Invoice, Integer> call(TableColumn<Invoice, Integer> param) {
            return new TableCell<Invoice, Integer>() {
                  @Override
                  protected void updateItem(Integer item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                        setText(null);
                     } else {
                        Invoice data = getTableView().getItems().get(getIndex());
                        // int unprocessed = noticesController.getCountUnProcess(data.getInvoiveID(), 0);
                        Integer noticeTotal = noticesController.getNotices(data.getInvoiveID()).size();
                        Boolean hasNotice = noticesController.existNotice(data.getInvoiveID());
                        if (data.getStatus() == 0) {
                           if (!hasNotice) {
                              setText(String.valueOf(noticeTotal));
                           } else {
                              setText(String.valueOf(noticeTotal));
                           }
                           setStyle("""
                              -fx-padding: 5 0 5 10;
                              -fx-font-weight: 700;
                              -fx-font-family: 'Tahoma';
                              -fx-text-fill: BLUE;
                              -fx-font-size: 14px;
                              """);
                        } else {
                           setText("");
                        }
                     }
                  }
            };
         }
      });
   }

   public void addRadioButtonColumnCheckPaid(TableColumn<Invoice, Void> checkPaidCol) {
      tableInvoice_column_checkPaid.setCellFactory(new Callback<TableColumn<Invoice, Void>, TableCell<Invoice, Void>>() {
         @Override
         public TableCell<Invoice, Void> call(TableColumn<Invoice, Void> param) {
            return new TableCell<Invoice, Void>() {
               private final RadioButton radioButton = new RadioButton();
               @Override
               protected void updateItem(Void item, boolean empty) {
                  super.updateItem(item, empty);
                  if (empty) {
                        setGraphic(null);
                  } else {
                     setStyle("-fx-padding: 5 0 0 30");
                     setGraphic(radioButton);
                     Invoice data = getTableView().getItems().get(getIndex());
                     if (data.getStatus() == 1) {
                        radioButton.setDisable(true);
                        radioButton.setSelected(true);
                     } else {
                        radioButton.setDisable(false);
                        radioButton.setSelected(false);
                        invoiceID = data.getInvoiveID();
                     }
                     radioButton.setOnAction(event -> {
                        try {
                           if (!isChecked) {
                              if (!noticesController.existNotice(data.getInvoiveID())) {
                                 AlertPopup.confirm("Alert Select Paid","When you selected Paid, you must ensure\nthis invoice has paid. If you not received amount\nfrom this invoice yet. You can Click 'NO' to Cancel!");
                                 if (AlertPopup.getConfirmationResult()) {
                                    tableInvoice_confirmPayment.setDisable(false);
                                    isChecked = true;
                                 } else {
                                    radioButton.setSelected(false);
                                    tableInvoice_confirmPayment.setDisable(true);
                                 }
                              } else {
                                 int count = noticesController.getCountUnProcess(invoiceID, 0);
                                 if (count > 0) {
                                       AlertPopup.warning("Recheck Notices", String.format("You have %s notices UnProcessed, recheck now!", count));
                                       radioButton.setSelected(false);
                                       tableInvoice_createNotice.setVisible(true);
                                 } else {
                                    if ( radioButton.isSelected()) {
                                       tableInvoice_confirmPayment.setDisable(false);
                                    } else {
                                       tableInvoice_confirmPayment.setDisable(true);
                                    }
                                 }
                              }
                           } else {
                              AlertPopup.confirm("Alert UnSelect Paid","When you Unselected Paid, This means you can not\nClick the Confirm Payment yet!");
                              if (AlertPopup.getConfirmationResult()) {
                                 tableInvoice_confirmPayment.setDisable(true);
                                 isChecked = false;
                              } else {
                                 radioButton.setSelected(true);
                                 tableInvoice_confirmPayment.setDisable(false);
                              }
                           }
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                     });
                  }
               }
            };
         }
      });
   }
   public void doubleClicked(MouseEvent event) {
      if (event.getClickCount() == 1) {
         Invoice isSelected = tableInvoice_tableViews.getSelectionModel().getSelectedItem();
         if (isSelected != null) {
            setInvoice(isSelected);
            setDataOnTable(isSelected);
            NoticeController.setInvoice(isSelected);
            tableInvoice_updateShowPDF.setDisable(false);
            if (isSelected.getStatus() == 1) {
               tableInvoice_confirmPayment.setDisable(true);
               tableInvoice_paidRadio.setDisable(true);
               tableInvoice_alertPayment.setDisable(true);
               tableInvoice_infrastructureID.setStyle(stylePaid());
               tableInvoice_paidRadio.setSelected(true);
               tableInvoice_newElectric.setDisable(true);
               tableInvoice_newWater.setDisable(true);
               tableInvoice_updateBTN.setDisable(true);
               // tableInvoice_sideBar.setDisable(true);
               setDisableIntoTable(true);
               tableInvoice_createNotice.setVisible(false);
            } else if (isSelected.getStatus() == 0) {
               if (noticesController.getCountUnProcess(isSelected.getInvoiveID(), 0) > 0) {
                  tableInvoice_confirmPayment.setDisable(true);
               } else {
                  tableInvoice_confirmPayment.setDisable(false);
               }
               tableInvoice_paidRadio.setDisable(false);
               tableInvoice_alertPayment.setDisable(false);
               tableInvoice_infrastructureID.setStyle(styleUnpaid());
               tableInvoice_paidRadio.setSelected(false);
               tableInvoice_newElectric.setDisable(false);
               tableInvoice_newWater.setDisable(false);
               tableInvoice_updateBTN.setDisable(false);
               // tableInvoice_sideBar.setDisable(false);
               setDisableIntoTable(false);
               tableInvoice_createNotice.setVisible(true);
               setTotalNoticeForBTN();
            }
         }
      } else if (event.getClickCount() != 1 && isChecked == null) {
         return;
      }
   }
   // todo SET ALL DATA FOR INFO LABEL & TEXTFIELD:
   private void setDataOnTable(Invoice isSelected) {
      tableInvoice_newElectric.setText(isSelected.getNewElectricity_number());
      tableInvoice_oldElectric.setText(isSelected.getOldElectricity_number());
      tableInvoice_newWater.setText(isSelected.getNewWater_number());
      tableInvoice_oldWater.setText(isSelected.getOldWater_number());
      tableInvoice_electricPriceTotal.setText("$ " + isSelected.getElectricity_price().toString());
      tableInvoice_waterPriceTotal.setText("$ " + isSelected.getWater_price().toString());
      tableInvoice_listedPrice.setText("$ " + isSelected.getPrice().toString());
      tableInvoice_totalPrice.setText("$ " + isSelected.getTotal_price().toString());
      tableInvoice_showDate.setText(isSelected.getCreate_at().toString());

      Long electricNO = Long.parseLong(tableInvoice_newElectric.getText()) - Long.parseLong(tableInvoice_oldElectric.getText());
      Long waterNO = Long.parseLong(tableInvoice_newWater.getText()) - Long.parseLong(tableInvoice_oldWater.getText());
      tableInvoice_electricityUsed.setText(electricNO.toString() + " kw/month");
      tableInvoice_waterUsed.setText(waterNO.toString() + " m3/month");
   }
   // todo SHOW FILE PDF:
   public void showFilePDF() throws Exception {
      FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(InvoicePDF.class.getResource("printInvoice.fxml")));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
      InvoicePDF invoicePDF = fxmlLoader.getController();
      invoicePDF.setData(getInvoice());
   }
   // todo SEARCH DIRECTLY ON TABLE NOT SEARCH BY SQL:
   private void searchOnTable() {
      ObservableList<Invoice> dataList =  FXCollections.observableList(invoiceController.getAll()
      .stream()
      .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
      .collect(Collectors.toList()));
      FilteredList<Invoice> filteredList = new FilteredList<Invoice>(dataList, p -> true);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/yyyy");
      tableInvoice_search.textProperty().addListener((obs, oldValue, newValue) -> {
         filteredList.setPredicate(invoice -> {
            if (newValue == null || newValue.isEmpty()) {
               return true;
            }
   
            String lowerCaseFilter = newValue.toLowerCase();

            if (invoice.getInvoiveID().toLowerCase().contains(lowerCaseFilter)) {
                  return true;
            } else if (invoice.getTotal_price().toLowerCase().contains(lowerCaseFilter)) {
                  return true;
            } else {
               try {
                  YearMonth searchYearMonth = YearMonth.parse(lowerCaseFilter, formatter);
                  YearMonth invoiceDate = YearMonth.from(invoice.getCreate_at().toLocalDate());
                  return invoiceDate.equals(searchYearMonth);
               } catch (DateTimeParseException e) {
                  return false;
               }
            }
         });
         tableInvoice_tableViews.setItems(filteredList);
      });
   }
   // todo SHOW TOOLTIP WHEN USER MOVE ON TABLE ROWS TO NOTICE USER MAYBE DOUBLE CLICK ON A ROW SPECIFICALLY TO EDIT CONTENT:
   public void showTooltip() {
      tableInvoice_tableViews.setRowFactory(e -> {
         TableRow<Invoice> row = new TableRow<>();
         Tooltip tooltip = new Tooltip("Double Click to select table row.!");
         tooltip.setShowDelay(Duration.millis(1000));
         tooltip.setWrapText(true);
         tooltip.setStyle(   "-fx-background-color: linear-gradient(to bottom right, #030f63, #06003f);" +
                              "-fx-font-family: Arial; " +
                              "-fx-font-size: 11px; " +
                              "-fx-text-fill: #fff;" +
                              "-fx-font-weight: 700;");
         tooltip.setPrefHeight(25);
            row.setOnMouseMoved(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent e) {
                  if (!row.isEmpty()) {
                     row.setTooltip(tooltip);
                  } else {
                     row.setTooltip(null);
                  }
               }
            });
         return row;
      });
   }
   // todo ADD MONTHS INTO COMBOBOX SELECT MONTH:
   private void showMonthComboBox(ComboBox<String> selectMonth) {
      List<String> months = new ArrayList<>();
      LocalDate dateCurrent = LocalDate.now();
      DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM");
      for (int i = 1; i <= 12; i++) {
         LocalDate monthDate = LocalDate.of(dateCurrent.getYear(), i, 1);
         String month = df.format(monthDate);
         months.add(month);
      }
      selectMonth.setItems(FXCollections.observableArrayList(months));
   }

   // todo ADD YEARS INTO COMBOBOX SELECT YEAR:
   private void showYearComboBox(ComboBox<String> selectYear) {
      List<String> years = new ArrayList<>();
      LocalDate presentYear = LocalDate.now();
      Integer currentYear = presentYear.getYear();
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy");
      for (int i = 0; i < 25; i++) {
         LocalDate yearDate = LocalDate.of(currentYear - i, 1, 1);
         String year = df.format(yearDate);
         years.add(year);
      }
      selectYear.setItems(FXCollections.observableArrayList(years));
   }   

   // todo FILTER INVOICE BY MONTH:
   private void filterMonth(ComboBox<String> selectMonth) {
      ObservableList<Invoice> dataList = FXCollections.observableList(invoiceController.getAll()
      .stream()
      .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
      .collect(Collectors.toList()));
      FilteredList<Invoice> filteredList = new FilteredList<Invoice>(dataList, p -> true);

      selectMonth.valueProperty().addListener((obs, oldValue, newValue) -> {
         filteredList.setPredicate(invoice -> {
            if (newValue == null || newValue.isEmpty()) {
               return true;
            }
            LocalDate invoiceMonth = invoice.getCreate_at().toLocalDate();
            String reformatInvoiceMonth = invoiceMonth.format(DateTimeFormatter.ofPattern("MMM"));
            return reformatInvoiceMonth.equals(newValue); 
         });
         tableInvoice_tableViews.setItems(filteredList);
      });
   }
   // todo FILTER INVOICE BY YEAR:
   private void filterYear(ComboBox<String> selectYear) {
      ObservableList<Invoice> invoices = FXCollections.observableArrayList(invoiceController.getAll()
      .stream()
      .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
      .collect(Collectors.toList()));
      FilteredList<Invoice> filteredList = new FilteredList<Invoice>(invoices, p -> true);

      selectYear.valueProperty().addListener((obs, oldValue, newValue) -> {
         filteredList.setPredicate(invoice -> {
            if (newValue == null || newValue.isEmpty()) {
               return true;
            }
            LocalDate invoiceYear = invoice.getCreate_at().toLocalDate();
            return invoiceYear.format(DateTimeFormatter.ofPattern("yyyy")).equals(newValue);
         });
         tableInvoice_tableViews.setItems(filteredList);
      });
   }

   // todo FILTER PRICE TOTAL:
   public void filterPrice(ComboBox<String> priceTotal) {
      priceTotal.setItems(FXCollections.observableArrayList(new String[]{
         "300 - 500", "600 - 700", "800 - 1000", " > 1000 "
      }));
      ObservableList<Invoice> invoices = FXCollections.observableArrayList(invoiceController.getAll()
      .stream()
      .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
      .collect(Collectors.toList()));
      FilteredList<Invoice> filterList = new FilteredList<Invoice>(invoices, p -> true);
      tableInvoice_tableViews.setItems(filterList);
      priceTotal.valueProperty().addListener((obs, oldValue, newValue) -> {
         filterList.setPredicate(invoice -> {
            if (newValue == null || newValue.isEmpty()) {
               return true;
            }
            String priceDuration = newValue.trim();
            if (priceDuration.contains("-")) {
               String[] splitPrice = priceDuration.split("-");
               BigDecimal minPrice = new BigDecimal(splitPrice[0].trim());
               BigDecimal maxPrice = new BigDecimal(splitPrice[1].trim());
               return new BigDecimal(invoice.getTotal_price()).compareTo(minPrice) >= 0 && new BigDecimal(invoice.getTotal_price()).compareTo(maxPrice) <= 0;
            }
            if (priceDuration.contains(">")) {
               BigDecimal maxPrice = new BigDecimal(priceDuration.replace(">", "").trim());
               return new BigDecimal(invoice.getTotal_price()).compareTo(maxPrice) > 0;
            }
            return false;
         });
         tableInvoice_tableViews.setItems(filterList);
      });
   }
   
   public void updateClick() throws Exception {
      if (getInvoice() == null) {
         AlertPopup.error("Unselected Table Row", "You missed select invoice to update!");
         return;
      }
      if (ValidateInput.isEmpty(tableInvoice_oldElectric.getText()) && ValidateInput.isEmpty(tableInvoice_oldWater.getText())) {
         AlertPopup.error("Alert Error", "You must double Click on a table row\nspecifically to UPDATE! ");
         tableInvoice_newElectric.requestFocus();
         return;
      }
      if (ValidateInput.isEmpty(tableInvoice_newElectric.getText().trim())) {
         AlertPopup.error("Input field New Electric Number", "Input field New Electricity Number not empty!");
         tableInvoice_newElectric.requestFocus();
         return;
      } else if (ValidateInput.isEmpty(tableInvoice_newWater.getText().trim())) {
         AlertPopup.error("Input field New Water Number", "Input field New Water Number not empty!");
         tableInvoice_newWater.requestFocus();
         return;
      }

      if (!ValidateInput.isNumber(tableInvoice_newElectric.getText())) {
         AlertPopup.error("Input field newElectric Number", "Input field New Electricity Number must be a number!");
         tableInvoice_newElectric.requestFocus();
         return;
      } else if (!ValidateInput.isNumber(tableInvoice_newWater.getText())) {
         AlertPopup.error("Input field newElectric Number", "Input field New Water Number must be a number!");
         tableInvoice_newWater.requestFocus();
         return;
      } else if (tableInvoice_newElectric.getText().length() > 6) {
         AlertPopup.error("Input field newElectric Number", "Electric Number must be under or equal 5 digits!");
         tableInvoice_newElectric.requestFocus();
         return;
      } else if (tableInvoice_newWater.getText().length() > 6) {
         AlertPopup.error("Input field newWater Number", "Water Number must be under or equal 5 digits!");
         tableInvoice_newWater.requestFocus();
         return;
      } else if (tableInvoice_newElectric.getText().matches("^[0]")) {
         AlertPopup.error("Input field newElectric Number", "Electric Number not begin with 0");
         tableInvoice_newElectric.requestFocus();
         return;
      } else if (tableInvoice_newWater.getText().matches("^[0]")) {
         AlertPopup.error("Input field New Water Number", "Electric Number not begin with 0");
         tableInvoice_newWater.requestFocus();
         return;
      }

      if (Long.parseLong(tableInvoice_newElectric.getText()) < Long.parseLong(tableInvoice_oldElectric.getText())) {
         AlertPopup.error("Input field Electric Number", "New Electric Number not less than Old Electric Number!!");
         tableInvoice_newElectric.requestFocus();
         return;
      } else if (Long.parseLong(tableInvoice_newWater.getText()) < Long.parseLong(tableInvoice_oldWater.getText())) {
         AlertPopup.error("Input field Water Number", "New Water Number not less than Old Water Number!!");
         tableInvoice_newWater.requestFocus();
         return;
      }

      AlertPopup.confirm("Update Invoice", "Have you ensure wanna update for this invoice?\nRecheck once more before update!");
      if (!AlertPopup.getConfirmationResult()) {
         return;
      }
      Long electricNO = Long.parseLong(tableInvoice_newElectric.getText()) - Long.parseLong(tableInvoice_oldElectric.getText());
      Long waterNO = Long.parseLong(tableInvoice_newWater.getText()) - Long.parseLong(tableInvoice_oldWater.getText());
      Double listedPrice = Double.parseDouble(getInvoice().getPrice());

      BigDecimal electPrice = (new BigDecimal(electricNO).multiply(electricPrice));
      BigDecimal waterPrices = (new BigDecimal(waterNO).multiply(waterPrice));
      BigDecimal totalPrice = new BigDecimal(listedPrice).add(waterPrices).add(electPrice);

      tableInvoice_electricPriceTotal.setText("$ " + electPrice.toString());
      tableInvoice_waterPriceTotal.setText("$ " + waterPrices.toString());
      tableInvoice_listedPrice.setText("$ " + listedPrice.toString());
      tableInvoice_totalPrice.setText("$ " + totalPrice.toString());

      Invoice newInvoice = new Invoice(
         getInvoice().getInvoiveID(),
         getInvoice().getInfrastructureID(),
         getInvoice().getPrice(),
         waterPrices.toString(),
         electPrice.toString(),
         getInvoice().getOldElectricity_number().toString(),
         tableInvoice_newElectric.getText(),
         getInvoice().getOldWater_number().toString(),
         tableInvoice_newWater.getText(),
         totalPrice.toString());


         // UPDATE INVOICE:
         Boolean isUpdated = invoiceController.update(newInvoice);
         if (isUpdated) {
            // UPDATE NEW ELECTRIC/ WATER FOR TABLE INFRASTRUCTURE AFTER UPDATED INVOICE:
            infrastructureController.updateWaterAndElectricity(tableInvoice_newElectric.getText(), tableInvoice_newWater.getText(), infrastructureID);
            // RESET INFO FOR INVOICE_PDF AFTER UPDATE INVOICE:
            setInvoice(invoiceController.getById(getInvoice().getInvoiveID()));
            // RENEW TABLE WHEN UPDATED INVOICE
            tableInvoice_tableViews.setItems(FXCollections.observableList(invoiceController.getAll()
            .stream()
            .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
            .collect(Collectors.toList())));
         }
         AlertPopup.success("Success", "Invoice Updated Successfully!");
         InfrastructureDetailScene.reload();
   }
   

   public void confirmPayment() throws Exception {
      if (isChecked) {
         invoiceController.updateStatus(invoiceID);
         tableInvoice_tableViews.setItems(FXCollections.observableList(invoiceController.getAll()
         .stream()
         .filter(invoice -> invoice.getInfrastructureID().equals(infrastructureID))
         .collect(Collectors.toList())));
         tableInvoice_newElectric.setDisable(true);
         tableInvoice_newWater.setDisable(true);
         tableInvoice_updateBTN.setDisable(true);
         tableInvoice_confirmPayment.setDisable(true);
         tableInvoice_createNotice.setVisible(false);
         tableInvoice_alertPayment.setDisable(true);
         AlertPopup.success("Alert Confirmation", "Confirmed Successfully!");
      } else {
         AlertPopup.error("Confirm Payment", "You must select PAID at Column 'Check Paid'\nbefore Confirm Payment!");
      }
   }

   public void setTotalNoticeForBTN() {
      if (getInvoice() == null) {
         notice_totalNotice.setText("");
         return;
      }

      if (noticesController.getNotices(getInvoice().getInvoiveID()) == null) {
         notice_totalNotice.setText("(0)");
      } else {
         Integer noticeTotal = noticesController.getNotices(getInvoice().getInvoiveID()).size();
         notice_totalNotice.setText("(" + noticeTotal.toString() + ")");
      }
   }
   public void setTotalNotice(Invoice data) {
      if (data == null) {
         notice_totalNotice.setText("");
         return;
      }

      if (noticesController.getNotices(data.getInvoiveID()) == null) {
         notice_totalNotice.setText("(0)");
      } else {
         Integer noticeTotal = noticesController.getNotices(data.getInvoiveID()).size();
         notice_totalNotice.setText("(" + noticeTotal.toString() + ")");
      }
   }

   public void alertPayment(MouseEvent event) throws Exception {
      if (invoiceController.getInvoice(infrastructureID) != null) {
         AlertPopup.confirm("Alert Payment", "Have you wanna send alert invoice\nin this month for tenants");
         if (AlertPopup.getConfirmationResult()) {
            TelegramSender.sendingTelegram(getInvoice(), new UserController().getById(InfrastructureDetailScene.getData().getUser_id()), infrastructureController.getById(infrastructureID).getName());
         }
         return;
      }
      AlertPopup.error("Alert Payment", "Already has not received any invoice to\nsend notification!");
   }

   private String styleUnpaid() {
      return """
            -fx-background-color: #ff6a6a;
            -fx-border-color: #ff0000;
            -fx-border-width: 1px;
            -fx-border-radius: 10px;
            -fx-background-radius: 10px;
            -fx-text-fill: #fff;
            """;
   }
   private String stylePaid() {
      return """
            -fx-background-color: #c1ff30;
            -fx-border-color: #b3ff00;
            -fx-border-width: 1px;
            -fx-border-radius: 10px;
            -fx-background-radius: 10px;
            """;
   }

   private void resetInfo() {
      tableInvoice_newElectric.setText("");
      tableInvoice_oldElectric.setText("");
      tableInvoice_newWater.setText("");
      tableInvoice_oldWater.setText("");
      tableInvoice_electricPriceTotal.setText("- -");
      tableInvoice_waterPriceTotal.setText("- -");
      tableInvoice_listedPrice.setText("- -");
      tableInvoice_totalPrice.setText("- -");
      tableInvoice_showDate.setText("- -");
      tableInvoice_electricityUsed.setText("- -");
      tableInvoice_waterUsed.setText("- -");
      tableInvoice_alertPayment.setDisable(true);
   }

   public void isChecked(ActionEvent event) throws Exception {
      if (!isChecked && getInvoice() != null) {
         AlertPopup.confirm("Alert Select Paid","When you selected Paid option, you must ensure\nthis invoice has paid. If you not received amount\nfrom this invoice yet. You can Click 'NO' to Cancel!");
         if (AlertPopup.getConfirmationResult()) {
            isChecked = true;
         } else {
            tableInvoice_paidRadio.setSelected(false);
            return;
         }
      }
      if (getInvoice() == null) {
         AlertPopup.warning("Alert Select Table Row", "You must be select row invoice specifically\nbefore checking Paid!");
         tableInvoice_paidRadio.setSelected(false);
         return;
      }
   }

   public void switchForm(ActionEvent event) {
      if (event.getSource() == tableInvoice_createNotice) {
         if (getInvoice() != null) {
            tableInvoice_tableForm.setVisible(false);
            tableInvoice_noticeForm.setVisible(true);
            showInfoInvoice();
            radioButton();
            showNotice();
            disable(true);
            tableInvoice_sideBar.setDisable(true);
            notice_prePayment_radio.setSelected(true);
            NoticeController.gridPaneNotice = notice_gridPaneNotices;
            descriptionLength();
            searchCase();
            calculateAmount();
            setCountNotice();
      } else {
            try {
               AlertPopup.warning("Alert Select Table Row", "You must be select Table Row Specifically\nto Create Notice!");
               return;
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      } else if (event.getSource() == noticeForm_backBTN) {
         tableInvoice_tableForm.setVisible(true);
         tableInvoice_noticeForm.setVisible(false);
         disable(false);
         tableInvoice_sideBar.setDisable(false);
         clearNotice();
      }
   }

   public void setCountNotice() {
      Integer noticeTotal = noticesController.getNotices(getInvoice().getInvoiveID()).size();
      Integer processedNotice = (int) noticesController.getNotices(getInvoice().getInvoiveID()).
                                 stream().filter(notice -> notice.getStatus() == 1).count();
      Integer unProcessedNotice = (int) noticesController.getNotices(getInvoice().getInvoiveID()).
                                 stream().filter(notice -> notice.getStatus() == 0).count();

      notice_total.setText(noticeTotal.toString());
      notice_processed.setText(processedNotice.toString());
      notice_unprocessed.setText(unProcessedNotice.toString());
   }

   public void setDisableIntoTable(Boolean contact) {
      tableInvoice_newElectric.setDisable(contact);
      tableInvoice_newWater.setDisable(contact);
      tableInvoice_updateBTN.setDisable(contact);
   }

   private void disable(Boolean bl) {
      tableInvoice_selectMonth.setDisable(bl);
      tableInvoice_selectYear.setDisable(bl);
      tableInvoice_selectPriceTotal.setDisable(bl);
      tableInvoice_search.setDisable(bl);
   }

   private void showNotice() {
      List<Notice> notices = noticesController.getNotices(getInvoice().getInvoiveID());
      if (notices != null) {
         notices.stream().forEach(notice -> {
            try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("notice.fxml")));
               AnchorPane lineNotice = fxmlLoader.load();
               NoticeController noticeController = fxmlLoader.getController();
               noticeController.setData(notice);
               if (col == 1) {
                  col = 0;
                  row++;
               }
               notice_gridPaneNotices.add(lineNotice, col++, row);
               GridPane.setMargin(lineNotice, new Insets(5, 5, 2, 5));
               if (notice.getStatus() == 0) {
                  noticePane.setStyle("""
                        -fx-background-color: linear-gradient(to bottom right, #ff5100, #ff0000);
                        -fx-border-radius: 10px;
                        -fx-background-radius: 10px;
                        """);
                  checkDone.setSelected(false);
               } else {
                  noticePane.setStyle("""
                        -fx-background-color: linear-gradient(to bottom right, #1f6b08, rgb(74, 202, 23));
                        -fx-border-radius: 10px;
                        -fx-background-radius: 10px;
                        """);
                  checkDone.setSelected(true);
               }
               checkDone.setOnAction(e -> {
                  try {
                        if (notice.getStatus() == 1) {
                           AlertPopup.confirm("Check Notice Process", "Do you want to change the status of\nthis NOTICE to Unprocessed?");
                           if (!AlertPopup.getConfirmationResult()) {
                              checkDone.setSelected(true);
                           } else {
                              noticesController.updateStatus(notice, 0);
                           }
                        } else if (notice.getStatus() == 0) {
                           AlertPopup.confirm("Check Notice Process", "Select 'YES' if you are sure this NOTICE is DONE");
                           if (!AlertPopup.getConfirmationResult()) {
                              checkDone.setSelected(false);
                           } else {
                              noticesController.updateStatus(notice, 1);
                           }
                        }
                        clearNotice();
                        showNotice();
                        setCountNotice();
                        reloadTableRow();
                     } catch (Exception ex) {
                        ex.printStackTrace();
                  }
               });
               deleteNotice.setOnAction(e -> {
                  try {
                     AlertPopup.confirm("Remove Notice ?", "Have you wanna remove this notice?\n\t'YES' to Remove\n\t'NO' to Cancel");
                     if (AlertPopup.getConfirmationResult()) {
                        clearNotice();
                        this.showNotice();
                        setCountNotice();
                        setTotalNoticeForBTN();
                        reloadTableRow();
                        AlertPopup.success("Removed notice", "Removed notice successfully!");
                     } else {
                        return;
                     }
                  } catch (Exception ex) {
                     ex.printStackTrace();
                  }
               });
            } catch (Exception e) {
               System.err.println(e.getMessage());
            }
         });
      } else {
         return;
      }
   }

   public void clearNotice() {
      notice_gridPaneNotices.getChildren().clear();
      col = 0;
      row = 0;
   }

   private void showInfoInvoice() {
      if (getInvoice() != null) {
         notice_totalPrice.setText(getInvoice().getTotal_price());
         notice_invoiceID.setText(getInvoice().getInvoiveID());
      }
   }

   public void createNotice() throws IOException {
      if (getRadioCase() == null) {
         AlertPopup.error("Alert Select Radio Button", "You must be select Case for Notice!");
         notice_prePayment_radio.requestFocus();
         return;
      }

      if (ValidateInput.isEmpty(notice_receivedAmount.getText())) {
         AlertPopup.error("Alert Field Receive dAmount", "Receive Amount field must filled up!");
         notice_receivedAmount.requestFocus();
         return;
      } else if (!ValidateInput.isNumber(notice_receivedAmount.getText())) {
         AlertPopup.error("Alert Field Receive dAmount", "Receive Amount field only digits number!");
         notice_receivedAmount.requestFocus();
         return;
      }

      if (ValidateInput.isEmpty(notice_description.getText().trim())) {
         AlertPopup.error("Alert Description", "You should write some description to\nnotice for case selected");
         notice_description.requestFocus();
         return;
      } else if (notice_description.getText().length() > 120) {
         AlertPopup.error("Alert Description", "You should write description short under 100 characters!");
         notice_description.requestFocus();
         return;
      }

      Notice notice = new Notice(
         getRadioCase(),
         notice_receivedAmount.getText(),
         notice_remainingAmount.getText(),
         notice_invoiceID.getText(),
         notice_description.getText()
      );

      Boolean isCreated = noticesController.create(notice);
      if (isCreated) {
         AlertPopup.success("Alert Create Notice", "Created Notice Successfully!");
         notice_receivedAmount.setText("");
         clearNotice();
         showNotice();
         setCountNotice();
         setTotalNoticeForBTN();
         reloadTableRow();
      } else {
         AlertPopup.error("Alert Create Notice", "Failed Create Notice!");
         return;
      }
   }

   public void calculateAmount() {
      notice_receivedAmount.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
               notice_remainingAmount.setText("");
               return;
            }

            if (ValidateInput.isNumber(newValue)) {
               try {
                  BigDecimal receivedAmount = new BigDecimal(newValue);
                  BigDecimal totalAmount = new BigDecimal(getInvoice().getTotal_price());
                  BigDecimal remainingAmount;

                  if (!noticesController.existNotice(getInvoice().getInvoiveID())) {
                        remainingAmount = totalAmount.subtract(receivedAmount);
                        if (receivedAmount.compareTo(totalAmount) > 0) {
                           notice_receivedAmount.setText(oldValue);
                           AlertPopup.error("Received Amount", String.format("Received Amount must be less than or equal Total Price:\nReceived Amount: \t %s", receivedAmount));
                        } else if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                           notice_receivedAmount.setText(oldValue);
                           AlertPopup.info("Recheck Previous Notices", String.format("Recheck notices nearest for Received Amount!"));
                        } else {
                           notice_remainingAmount.setText(String.valueOf(remainingAmount));
                        }
                  } else {
                        Notice latestNotice = noticesController.getNotice(getInvoice().getInvoiveID());
                        BigDecimal remainingFromNotice = new BigDecimal(latestNotice.getRemaining_amount());
                        remainingAmount = remainingFromNotice.subtract(receivedAmount);
                        if (receivedAmount.compareTo(BigDecimal.ZERO) < 0) {
                           notice_receivedAmount.setText(oldValue);
                           AlertPopup.error("Received Amount", String.format("Received Amount not valid:\nReceived Amount: \t %s", receivedAmount));
                        } else if (remainingAmount.compareTo(BigDecimal.ZERO) < 0) {
                           notice_receivedAmount.setText(oldValue);
                           AlertPopup.info("Recheck Remaining Amount", String.format("The Remaining Amount has received enough!\nRecheck your previous notices nearest!"));
                        } else {
                           notice_remainingAmount.setText(String.valueOf(remainingAmount));
                        }
                  }
               } catch (NumberFormatException e) {
                     try {
                        AlertPopup.error("Error", "Received Amount must be a valid number.");
                     } catch (IOException e1) {
                        e1.printStackTrace();
                     }
               } catch (Exception e) {
                     try {
                        AlertPopup.error("Error", "An error occurred while calculating the amount.");
                     } catch (IOException e1) {
                        e1.printStackTrace();
                     }
               }
            } else {
               try {
                  notice_receivedAmount.setText(newValue.substring(0, newValue.length() - 1));
                  AlertPopup.error("Alert Received Amount", "Received Amount field only accepts digits!");
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
      });
   }


   private List<RadioButton> radioButton() {
      radioButton = new ArrayList<>();
      radioButton.add(notice_extend_radio);
      radioButton.add(notice_others_radio);
      radioButton.add(notice_prePayment_radio);
      return radioButton;
   }

   private String getRadioCase() {
      String radioCase = null;
      for (RadioButton radio : radioButton()) {
         if (radio.isSelected()) {
            radioCase = radio.getText();
            break;
         }
      }
      return radioCase;
   }

   public void showRadioExplain(ActionEvent e) {
      if (e.getSource() == notice_prePayment_radio) {
         notice_labelExplain.setText("*** Payment: Pre_payment, this means when you pre receive an amount which on total price invoice's");
      } else if (e.getSource() == notice_extend_radio) {
         notice_labelExplain.setText("*** Extend: If the tenant asked an duration to pay, you can select case \"Extend\"");
      } else if (e.getSource() == notice_others_radio) {
         notice_labelExplain.setText("*** Others: For this case, you can write some description within content box to remember.");
      }
   }

   public void clearAllField() {
      notice_receivedAmount.setText("");
      notice_description.setText("");
      notice_remainingAmount.setText("");
   }

   private void descriptionLength() {
      notice_description.textProperty().addListener((obs, oldValue, newValue) -> {
         if (!newValue.trim().isEmpty() || newValue.trim() != null) {
            int count = newValue.length();
            notice_countChars.setText(String.format("%s /", count));
            if (count > 120) {
               notice_description.setText(newValue.substring(0, 120));
               try {
                  AlertPopup.error("Description Length", "Content too long, you should write a description short\nunder 165 characters!");
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
            return;
         } else {
            notice_countChars.setText("0");
            return;
         }
      });
   }
   public void searchCase() {
      notice_searchCase.textProperty().addListener((obs, oldValue, newValue) -> {
         List<Notice> listNotices = noticesController.getList(newValue, getInvoice().getInvoiveID());
         clearNotice();
         if (listNotices != null) {
            listNotices.stream().forEach(notice -> {
               if (notice != null) {
                  try {
                     FXMLLoader fxmlLoader = new FXMLLoader();
                     fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("notice.fxml")));
                     AnchorPane lineNotice = fxmlLoader.load();
                     NoticeController noticeController = fxmlLoader.getController();
                     noticeController.setData(notice);
                     if (col == 1) {
                        col = 0;
                        row++;
                     }
                     notice_gridPaneNotices.add(lineNotice, col++, row);
                     GridPane.setMargin(lineNotice, new Insets(5, 5, 2, 5));
                     deleteNotice.setOnAction(e -> {
                        try {
                           AlertPopup.confirm("Remove Notice ?", "Have you wanna remove this notice?\n\t'YES' to Remove\n\t'NO' to Cancel");
                           if (AlertPopup.getConfirmationResult()) {
                              clearNotice();
                              this.showNotice();
                              AlertPopup.success("Removed notice", "Removed notice successfully!");
                           } else {
                              return;
                           }
                        } catch (Exception ex) {
                           ex.printStackTrace();
                        }
                     });
                  } catch (Exception e) {
                     System.out.println(e.getMessage());
                  }
               }
            });
         }
      });
   }

   public static void main(String[] args) {
   }
}
