package io.xpx.main.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageLoaderController extends AbstractController implements Initializable {

	@FXML
	private JFXButton load;
	
	@FXML 
	private TextField hash;
	

	@FXML
	private WebView webView;

	private File file;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		webView.getEngine().load("https://localhost:8881/download/file?nemHash=511ad868fe67f315f30b5962aa285deabd71e59aa8801dbe3846e9b1ff633f3f&transferMode=bytes");
	}

	public void loadImage(Event event) throws ApiException {
		webView.getEngine().load("http://localhost:8881/download/file?nemHash="+hash.getText()+"&transferMode=bytes");

	}


}
