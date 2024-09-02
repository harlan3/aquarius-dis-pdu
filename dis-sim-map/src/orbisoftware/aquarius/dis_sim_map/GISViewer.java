/*
 *  Aquarius DIS PDU Suite
 *
 *  Copyright (C) 2024 Harlan Murphy
 *  Orbis Software - orbisoftware@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package orbisoftware.aquarius.dis_sim_map;

import java.awt.*;
import java.net.*;
import java.io.*;
import uk.ac.leeds.ccg.geotools.*;
import uk.ac.leeds.ccg.widgets.ToolBar;

public class GISViewer extends Frame {
	
	Viewer view = new Viewer();

	GISViewer(String[] args) {
		ToolBar tools = new ToolBar(view);
		setLayout(new BorderLayout());
		add(view, "Center");
		add(tools, "North");

		try {
			for (int i = 0; i < args.length; i++) {
				loadMaps(args[i]);
			}
		} catch (IOException e) {
			System.out.println("Error loading map file " + e);
		}
		
		int w = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 
				MainApplication.secondaryGUIWidth - MainApplication.xWidthAdjustPrimary;
		int h = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - MainApplication.bottomBorder;
		
		setSize(w, h);
		setVisible(true);
	}

	public void loadMaps(String filename) throws IOException {

		URL url = new URL("file:" + filename);

		// Dis theme
		DisLayer disLayer = DisLayer.getInstance();
		Theme disTheme = new Theme(disLayer);
		view.addTheme(disTheme);

		// Shapefile theme
		ShapefileReader sfr = new ShapefileReader(url);
		Theme t = sfr.getTheme();
		view.addTheme(t);
	}
}
