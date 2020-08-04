package com.ssafy.indieAndBob.user.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Repository
public class firebaseT {
	static Firestore db;
	public static void initDb() throws IOException {
		InputStream serviceAccount = new FileInputStream("src/main/resources/indie-and-bob-firebase-adminsdk-55xq2-7113ee938d.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(credentials)
				.build();
		FirebaseApp.initializeApp(options);
		
		db = FirestoreClient.getFirestore();
	}
	
	public static void insert() throws InterruptedException, ExecutionException, IOException {
		initDb();
		DocumentReference docRef = db.collection("users").document("alovelace");
		// Add document data  with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("first", "Ada");
		data.put("last", "Lovelace");
		data.put("born", 1815);
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		System.out.println("Update time : " + result.get().getUpdateTime());
	}
}
