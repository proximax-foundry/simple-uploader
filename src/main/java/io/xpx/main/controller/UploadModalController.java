package io.xpx.main.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;
import org.nem.core.node.NodeEndpoint;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import io.nem.xpx.exceptions.ApiException;
import io.nem.xpx.exceptions.PeerConnectionNotFoundException;
import io.nem.xpx.facade.connection.LocalHttpPeerConnection;
import io.nem.xpx.facade.connection.PeerConnection;
import io.nem.xpx.facade.upload.Upload;
import io.nem.xpx.facade.upload.UploadBinaryParameter;
import io.nem.xpx.facade.upload.UploadException;
import io.nem.xpx.facade.upload.UploadResult;
import io.nem.xpx.factory.ConnectionFactory;
import io.nem.xpx.service.intf.NodeApi;
import io.nem.xpx.service.local.LocalNodeApi;
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
	private JFXTextField nemHash;
	@FXML
	private JFXTextField searchHash;
	
	@FXML
	private Label peerId;
	
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
	PeerConnection peerConnection;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		peerConnection = new LocalHttpPeerConnection(
				ConnectionFactory.createNemNodeConnection("testnet","http", "104.128.226.60", 7890),
				ConnectionFactory.createIPFSNodeConnection("/ip4/127.0.0.1/tcp/5001"));
		progressBar.setProgress(0);
		
//		gateways.setItems(FXCollections.observableArrayList(
//				"http://localhost:8881",
//				"https://gateway.proximax.io/xpx"));
		
		try {
			peerId.setText(((LocalNodeApi)peerConnection.getNodeApi()).getLocalProximaXIpfsConnection().config.show().get("Identity").toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

		try {
		//if (file != null) {
			progressBar.setProgress(0.1d);
			
			
			UploadBinaryParameter binaryParameter = UploadBinaryParameter.create()
					.senderPrivateKey("deaae199f8e511ec51eb0046cf8d78dc481e20a340d003bbfcc3a66623d09763")
					.receiverPublicKey("36e6fbc1cc5c3ef49d313721650b98d7d7d126a4f731d70071f4f3b4798cdc85")
					.data(FileUtils.readFileToByteArray(file))
					.contentType(new MimetypesFileTypeMap().getContentType(file))
					.name(file.getName())
					.build();
			progressBar.setProgress(0.5);
			Upload upload = new Upload(peerConnection);
			UploadResult uploadResult = upload.uploadBinary(binaryParameter);
			progressBar.setProgress(0.7);
			hash.setText("https://testnet.gateway.proximax.io/xipfs/"+uploadResult.getIpfsHash());
			nemHash.setText("https://testnet.gateway.proximax.io/xpxfs/"+uploadResult.getNemHash());
			
			progressBar.setProgress(1);
			this.file = null;
			this.fileName.setText("");
		//}
		}catch (Exception e) {
			//
		}

	}
	
	public void mashLinks(Event event) {
		
		String html = "<a href='http://localhost:8881/download/file?nemHash=511ad868fe67f315f30b5962aa285deabd71e59aa8801dbe3846e9b1ff633f3f&transferMode=bytes'>asa</a>";
		webView.getEngine().loadContent(html,"text/html");
	}
	
	

}
