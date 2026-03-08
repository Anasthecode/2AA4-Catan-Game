package com.team22.catan.board;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  AxialPositionTest.class,
  BoardTest.class,
  EdgeTest.class,
  NodeTest.class,
  TileTest.class
})
public class BoardTests {}