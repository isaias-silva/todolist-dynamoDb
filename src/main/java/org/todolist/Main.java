package org.todolist;

import java.util.Scanner;
import java.util.UUID;

import org.todolist.services.DynamoDbService;


public class Main {

	public static void main(String[] args) {
		boolean inLoop = true;
		Scanner sc = new Scanner(System.in);
		DynamoDbService dynamoDbService = new DynamoDbService("todo");

		while (inLoop) {
			try {
				System.out.println(
					"lista de tarefas:\n1 - criar tarefa\n 2 - apagar tarefa \n 3 - buscar tarefa\n 0 - sair");
				int opt = sc.nextInt();
				switch (opt) {
					case 1:

						String taskId =  UUID.randomUUID().toString();
						System.out.print("Digite a descrição da tarefa: ");
						String description = sc.nextLine();


						dynamoDbService.putItem(taskId,description);
						System.out.println("Tarefa adicionada!");
						break;
					case 2:
						System.out.print("Digite o ID da tarefa para apagar: ");
						String deleteId = sc.nextLine();
						dynamoDbService.deleteItem("id", deleteId);
						System.out.println("Tarefa apagada!");

						break;
					case 3:
						System.out.print("Digite o ID da tarefa para buscar: ");
						String searchId = sc.nextLine();
						String response = dynamoDbService.getItem("id", searchId);
						if (response.isEmpty()) {
							System.out.println("Tarefa não encontrada.");
						} else {
							System.out.println(
								"Tarefa encontrada: " + response);
						}
						break;
					case 0:
						System.out.println("até outro dia...");
						inLoop = false;
						break;
					default:
						System.out.println("Opção inválida!");
				}
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
				sc.nextLine();
			}

		}
		sc.close();
	}
}