package com.code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MultiThreadedEchoServer extends JFrame {
	private JTextArea logTextArea;

	public MultiThreadedEchoServer() {
		setTitle("MultiThreaded Echo Server");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logTextArea);
		add(scrollPane, BorderLayout.CENTER);

		JButton startButton = new JButton("Start Server");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("In server started");
				startServer();
				startButton.setEnabled(false);
			}
		});
		add(startButton, BorderLayout.SOUTH);
	}

	private void startServer() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(12345);
			System.out.println("Server started on port 12345.");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected: " + clientSocket.getInetAddress());

				Thread clientThread = new Thread(new ClientHandler(clientSocket));
				clientThread.start();
			}
		} catch (IOException e) {
			log("Error starting the server: " + e.getMessage());
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					log("Error closing the server socket: " + e.getMessage());
				}
			}
		}
	}

	private void log(String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				logTextArea.append(message + "\n");
			}
		});
	}

	private class ClientHandler implements Runnable {
		private Socket clientSocket;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		public void run() {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

				String inputLine;
				while ((inputLine = reader.readLine()) != null) {
					writer.println("Server: " + inputLine);
				}
			} catch (IOException e) {
				log("Error handling client: " + e.getMessage());
			} finally {
				try {
					clientSocket.close();
					log("Client disconnected: " + clientSocket.getInetAddress());
				} catch (IOException e) {
					log("Error closing client socket: " + e.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MultiThreadedEchoServer server = new MultiThreadedEchoServer();
				server.setVisible(true);
			}
		});
	}
}
