package com.centertonnursery.production.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXTable;

public class App {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Request Grid Prototype");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Category");
		model.addColumn("Genus");
		model.addColumn("Variety");
		model.addColumn("Size");
		model.addColumn("Source");
		model.addColumn("Request");
		model.addColumn("Week");

		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", 800, 32 });
		model.addRow(new Object[] { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", 1600, 33 });

		JXTable table = new JXTable(model);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel
				.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		for (int i = 0; i < 5; i++) {
			table.getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
						final boolean hasFocus, final int row, final int column) {
					final Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					cell.setForeground(Color.green.darker());
					return cell;
				}
			});
		}
		for (int i = 5; i < 7; i++) {
			table.getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
						final boolean hasFocus, final int row, final int column) {
					final JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
							column);
					cell.setHorizontalAlignment(SwingConstants.RIGHT);
					cell.setForeground(Color.blue.darker());
					return cell;
				}
			});
		}

		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		sorter.setComparator(5, new Comparator<Integer>() {

			@Override
			public int compare(final Integer o1, final Integer o2) {
				if (o1 == o2) {
					return 0;
				}
				if (o1 == null) {
					return -1;
				}
				if (o2 == null) {
					return 1;
				}
				return Integer.compare(o1, o2);
			}
		});
		table.setRowSorter(sorter);
		table.packAll();
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
