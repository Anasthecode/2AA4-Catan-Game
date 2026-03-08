package com.team22.catan.board;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.team22.catan.board.AxialPosition.Direction;

public class AxialPositionTest {
    @Test
    public void addZeroTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition newPos = pos.add(new AxialPosition(0, 0));
      assertEquals(0, newPos.getQ());
      assertEquals(0, newPos.getR());
    }

    @Test
    public void neighbourRightTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition neighbour = pos.neighbour(Direction.RIGHT);
      assertEquals(1, neighbour.getQ());
      assertEquals(0, neighbour.getR());
    }

    @Test
    public void neighbourUpRightTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition neighbour = pos.neighbour(Direction.UPRIGHT);
      assertEquals(1, neighbour.getQ());
      assertEquals(-1, neighbour.getR());
    }

    @Test
    public void neighbourUpLeftTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition neighbour = pos.neighbour(Direction.UPLEFT);
      assertEquals(0, neighbour.getQ());
      assertEquals(-1, neighbour.getR());
    }

    @Test
    public void neighbourLeftTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition neighbour = pos.neighbour(Direction.LEFT);
      assertEquals(-1, neighbour.getQ());
      assertEquals(0, neighbour.getR());
    }

    @Test
    public void neighbourDownLeftTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition neighbour = pos.neighbour(Direction.DOWNLEFT);
      assertEquals(-1, neighbour.getQ());
      assertEquals(1, neighbour.getR());
    }

    @Test
    public void neighbourDownRightTest() {
      AxialPosition pos = new AxialPosition(0, 0);
      AxialPosition neighbour = pos.neighbour(Direction.DOWNRIGHT);
      assertEquals(0, neighbour.getQ());
      assertEquals(1, neighbour.getR());
    }
}