package io.xpx.main.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.nem.core.node.NodeEndpoint;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.nem.ApiException;
import io.nem.xpx.builder.UploadFileParameterBuilder;
import io.nem.xpx.facade.Upload;
import io.nem.xpx.facade.connection.LocalHttpPeerConnection;
import io.nem.xpx.facade.model.UploadData;
import io.nem.xpx.model.PeerConnectionNotFoundException;
import io.nem.xpx.model.UploadException;
import io.nem.xpx.model.UploadFileParameter;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadModalController extends AbstractController implements Initializable {

	@FXML
	private JFXButton upload;
	
	@FXML
	private JFXButton browse;

	@FXML
	private JFXTextField hash;
	
	@FXML
	private JFXTextField searchHash;
	
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private ChoiceBox<String> gateways;
	
	@FXML 
	private WebView webView;
	
	
	@FXML
	private Label fileName;

	private File file;
	private List<File> files;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gateways.setItems(FXCollections.observableArrayList("http://localhost:8881","https://gateway.proximax.io/xpx"));
		
	}

	public void upload(Event event) throws ApiException {
		UploadFileParameter uploadFile = UploadFileParameterBuilder
				.senderPrivateKey("deaae199f8e511ec51eb0046cf8d78dc481e20a340d003bbfcc3a66623d09763")
				.recipientPublicKey("36e6fbc1cc5c3ef49d313721650b98d7d7d126a4f731d70071f4f3b4798cdc85").data(file)
				.keywords(">>>").build();

	}

	public void loadMultipleFileChooser(Event event) throws IOException, ApiException, PeerConnectionNotFoundException, UploadException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage stage = new Stage();
		this.files = fileChooser.showOpenMultipleDialog(stage);
	}
	
	public void loadFileChooser(Event event) throws IOException, ApiException, PeerConnectionNotFoundException, UploadException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage stage = new Stage();
		this.file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			this.fileName.setText(file.getAbsolutePath());
		}
	}

	
	public void uploadfileChooser(Event event) throws IOException, ApiException, PeerConnectionNotFoundException, UploadException {

		
		if (file != null) {
			progressBar.setProgress(0.1d);
			LocalHttpPeerConnection localPeerConnection = new LocalHttpPeerConnection(
					new NodeEndpoint("http", "104.128.226.60", 7890));
			progressBar.setProgress(0.3);
			UploadFileParameter fileParameter = UploadFileParameterBuilder
					.senderPrivateKey("deaae199f8e511ec51eb0046cf8d78dc481e20a340d003bbfcc3a66623d09763")
					.recipientPublicKey("36e6fbc1cc5c3ef49d313721650b98d7d7d126a4f731d70071f4f3b4798cdc85").data(file)
					.keywords(file.getName()).build();
			progressBar.setProgress(0.5);
			Upload upload = new Upload(localPeerConnection);
			UploadData uploadData = upload.uploadFile(fileParameter);
			progressBar.setProgress(0.7);
			hash.setText(uploadData.getNemHash());
			progressBar.setProgress(1d);
			this.file = null;
			this.fileName.setText("");
		}

	}
	
	public void mashLinks(Event event) {
		
		String html = "<a href='http://localhost:8881/download/file?nemHash=511ad868fe67f315f30b5962aa285deabd71e59aa8801dbe3846e9b1ff633f3f&transferMode=bytes'>asa</a>";
		webView.getEngine().loadContent(html,"text/html");
	}
	
	

}
