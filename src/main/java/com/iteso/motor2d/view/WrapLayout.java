package com.iteso.motor2d.view;

import java.awt.*;

public class WrapLayout extends FlowLayout 
{

    public WrapLayout() 
	{
        super();
    }

    public WrapLayout(int align, int hgap, int vgap) 
	{
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) 
	{
        return computeLayoutSize(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) 
	{
        return computeLayoutSize(parent);
    }

    /**
     * Calcula el tamaño total necesario para acomodar los componentes
     * haciendo wrap cuando el ancho disponible se llena
	 * 
     */
    private Dimension computeLayoutSize(Container parent) 
	{
        synchronized (parent.getTreeLock()) 
		{

            int hgap = getHgap(); // espacio horizontal entre componentes
            int vgap = getVgap();
            Insets insets = parent.getInsets(); // los insets son los bordes internos del contenedor

            // ancho disponible dentro del panel
            int availableWidth = parent.getWidth();

            // si aun no tiene tamaño real 
            if (availableWidth <= 0) 
			{
                availableWidth = Integer.MAX_VALUE; // para que no haga wrap, porque no hay tamaño definido
            }

            availableWidth -= insets.left + insets.right;

            int x = 0;     // ancho acumulado de la fila actual
            int y = insets.top + vgap; // altura total del layout
            int rowHeight = 0;

            for (Component comp : parent.getComponents()) 
			{
                if (!comp.isVisible())
                    continue;

                Dimension d = comp.getPreferredSize();

                // si no cabe en la fila actual, se pone en la siguiente linea
                if (x + d.width > availableWidth) 
				{
                    y += rowHeight + vgap;  // pasamos a la siguiente línea
                    x = 0;
                    rowHeight = 0;
                }

                // acomodar componente
                x += d.width + hgap;
                rowHeight = Math.max(rowHeight, d.height);
            }

            // suma la última fila
            y += rowHeight + insets.bottom;

            return new Dimension(availableWidth, y);
        }
    }
}
