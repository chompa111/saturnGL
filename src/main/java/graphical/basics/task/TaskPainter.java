package graphical.basics.task;

import java.awt.*;

public class TaskPainter {

    public static void paint(Graphics g, Task t, int x, int y) {

        if (t instanceof ParalelTask) {
            paint(g, (ParalelTask) t, x, y);
        } else if (t instanceof SequenceTask) {
            paint(g, (SequenceTask) t, x, y);
        } else if (t instanceof RepeatTask) {
            paint(g, (RepeatTask) t, x, y);
        } else if (t instanceof WaitTask) {
            paint(g, (WaitTask) t, x, y);
        }else {
            g.setColor(Color.white);
            g.fillOval(x-10, y-10, 20, 20);
        }


    }


    static void paint(Graphics g, ParalelTask task, int x, int y) {
        for (int i = 0; i < task.taskList.size(); i++) {
            paint(g, task.taskList.get(i), x, y + 50 * i);
            g.setColor(Color.green);
            g.drawLine(x, y, x, y + 50);
        }
    }

    static void paint(Graphics g, SequenceTask task, int x, int y) {
        for (int i = 0; i < task.taskList.size(); i++) {
            paint(g, task.taskList.get(i), x + 50 * i, y);
            g.setColor(Color.red);
            g.drawLine(x, y, x + 50 * i, y);
        }
    }

    static  void paint(Graphics g, RepeatTask task, int x, int y) {
        g.drawString(task.countTimes + "/" + task.times, x, y);
        paint(g, task.task, x + 30, y);
    }

    static void paint(Graphics g, WaitTask task, int x, int y) {
        g.setColor(Color.cyan);
        g.fillOval(x-10, y-10, 20, 20);
    }


}
