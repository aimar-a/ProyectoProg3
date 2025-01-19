//FUENTE-EXTERNA
// URL: https://github.com/DJ-Raven/java-swing-switch-button/tree/main
// ADAPTADO
package gui.mainMenu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//IAG: ChatGPT y GitHub Copilot
//ADAPTADO: Anadir funcionalidades y autocompeltado
public class SwitchButton extends Component {
    private Timer timer;
    private float location;
    private boolean selected;
    private boolean mouseOver;
    private static final float SPEED = 1f;
    private List<EventSwitchSelected> events;

    public SwitchButton() {
        initialize();
    }

    private void initialize() {
        setBackground(new Color(0, 174, 255));
        setPreferredSize(new Dimension(50, 25));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        events = new ArrayList<>();
        location = 2;
        timer = createTimer();
        addMouseListener(createMouseAdapter());
    }

    private Timer createTimer() {
        return new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                updateLocation();
            }
        });
    }

    private void updateLocation() {
        int endLocation = isSelected() ? getWidth() - getHeight() + 2 : 2;
        if ((isSelected() && location < endLocation) || (!isSelected() && location > endLocation)) {
            location += isSelected() ? SPEED : -SPEED;
            repaint();
        } else {
            timer.stop();
            location = endLocation;
            repaint();
        }
    }

    private MouseAdapter createMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me) && mouseOver) {
                    setSelected(!selected);
                }
            }
        };
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        timer.start();
        runEvent();
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        float alpha = getAlpha();
        if (alpha < 1) {
            g2.setColor(Color.GRAY);
            g2.fillRoundRect(0, 0, width, height, 25, 25);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, 25, 25);
        g2.setColor(getForeground());
        g2.setComposite(AlphaComposite.SrcOver);
        g2.fillOval((int) location, 2, height - 4, height - 4);
        super.paint(grphcs);
    }

    private float getAlpha() {
        float width = getWidth() - getHeight();
        float alpha = (location - 2) / width;
        return Math.clamp(alpha, 0, 1);
    }

    private void runEvent() {
        for (EventSwitchSelected event : events) {
            event.onSelected(selected);
        }
    }

    public void addEventSelected(EventSwitchSelected event) {
        events.add(event);
    }
}
