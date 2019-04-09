package com.gmail.cactuscata.setnametag;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Main extends JavaPlugin implements Listener {

	private final String ERROR = "§4ERROR : ";

	public void onEnable() {

		getCommand("settag").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("settag")) {

			if (args.length < 1) {
				sender.sendMessage(ERROR + "Veillez préciser un argument !");
				return true;
			}

			if (args[0].equalsIgnoreCase("prefix")) {

				if (args.length < 2) {
					sender.sendMessage(ERROR + "Veillez préciser le nom du joueur !");
					return true;
				}

				if (args.length < 3) {
					sender.sendMessage(ERROR + "Veillez préciser le prefix !");
					return true;
				}

				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getMainScoreboard();
				Player player = Bukkit.getPlayerExact(args[1]);
				if (player != null && player.isOnline()) {

					createTeam(player.getName(), board);
					joinTeam(player.getName(), board);
					Team team = board.getTeam(player.getName());
					team.setPrefix(args[2].substring(0, Math.min(args[2].length(), 16)).replace('&', '§'));
					return true;

				}

				sender.sendMessage(ERROR + "Le joueur " + args[1] + " n'est pas en ligne ou n'existe pas !");
				return true;

			}

			if (args[0].equalsIgnoreCase("suffix")) {

				if (args.length < 2) {
					sender.sendMessage(ERROR + "Veillez préciser le nom du joueur !");
					return true;
				}

				if (args.length < 3) {
					sender.sendMessage(ERROR + "Veillez préciser le suffix !");
					return true;
				}

				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getMainScoreboard();
				Player player = Bukkit.getPlayerExact(args[1]);
				if (player != null && player.isOnline()) {

					createTeam(player.getName(), board);
					joinTeam(player.getName(), board);
					Team team = board.getTeam(player.getName());
					team.setSuffix(args[2].substring(0, Math.min(args[2].length(), 16)).replace('&', '§'));
					return true;

				}

				sender.sendMessage(ERROR + "Le joueur " + args[1] + " n'est pas en ligne ou n'existe pas !");
				return true;

			}

			if (args[0].equalsIgnoreCase("clear")) {

				if (args.length < 2) {
					sender.sendMessage(ERROR + "Veillez préciser le joueur !");
					return true;
				}

				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getMainScoreboard();
				Player player = Bukkit.getPlayerExact(args[1]);
				if (player != null && player.isOnline()) {

					Team team = board.getTeam(player.getName());

					if (board.getTeam(player.getName()) == null) {
						sender.sendMessage(ERROR + "Le joueur n'a déjà plus de team !");
						return true;
					}

					team.unregister();
					return true;

				}

				sender.sendMessage(ERROR + "Le joueur " + args[1] + " n'est pas en ligne ou n'existe pas !");
				return true;

			}

			sender.sendMessage(ERROR + "La section " + args[0] + " n'est pas valide !");
			return true;

		}
		return false;
	}

	private void createTeam(String NameOfPlayer, Scoreboard board) {
		if (board.getTeam(NameOfPlayer) == null)
			board.registerNewTeam(NameOfPlayer);
	}

	@SuppressWarnings("deprecation")
	private void joinTeam(String NameOfPlayer, Scoreboard board) {

		Team team = board.getTeam(NameOfPlayer);
		Player player = Bukkit.getPlayerExact(NameOfPlayer);
		team.addPlayer(player);

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		Player player = event.getPlayer();
		if (player != null && player.isOnline()) {

			Team team = board.getTeam(player.getName());

			if (board.getTeam(player.getName()) == null)
				return;

			team.unregister();

		}

	}

}
