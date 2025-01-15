package com.jkpr.chinesecheckers;

import com.jkpr.chinesecheckers.server.gamelogic.rules.AbstractRules;
import com.jkpr.chinesecheckers.server.gamelogic.rules.FastPacedRules;
import com.jkpr.chinesecheckers.server.gamelogic.Move;
import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.CCBoard;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class FastPacedTest extends TestCase {
    public static Test suite()
    {
        return new TestSuite( FastPacedTest.class );
    }
    public void testBasic(){
        AbstractRules rules=new FastPacedRules(2);
        AbstractBoard board=new CCBoard();

        board.generate(rules);

        UpdateMessage message=rules.isValidMove(board,rules.getPlayer(0),new Move(3,-5,3,-4));
        assertEquals("UPDATE 3 -5 3 -4 NEXT_ID 1 WIN_ID",message.serialize());
        message=rules.isValidMove(board,rules.getPlayer(1),new Move(-3,5,-3,4));
        assertEquals("UPDATE -3 5 -3 4 NEXT_ID 0 WIN_ID",message.serialize());

        message=rules.isValidMove(board,rules.getPlayer(0),new Move(3,-6,3,-2));
        assertEquals("UPDATE 3 -6 3 -2 NEXT_ID 1 WIN_ID",message.serialize());
        message=rules.isValidMove(board,rules.getPlayer(1),new Move(-3,6,-3,2));
        assertEquals("UPDATE -3 6 -3 2 NEXT_ID 0 WIN_ID",message.serialize());

        message=rules.isValidMove(board,rules.getPlayer(0),new Move(3,-2,3,-1));
        assertEquals("UPDATE 3 -2 3 -1 NEXT_ID 1 WIN_ID",message.serialize());
        message=rules.isValidMove(board,rules.getPlayer(1),new Move(-3,2,-3,1));
        assertEquals("UPDATE -3 2 -3 1 NEXT_ID 0 WIN_ID",message.serialize());

        message=rules.isValidMove(board,rules.getPlayer(0),new Move(3,-1,3,0));
        assertEquals("UPDATE 3 -1 3 0 NEXT_ID 1 WIN_ID",message.serialize());
        message=rules.isValidMove(board,rules.getPlayer(1),new Move(-3,1,-3,0));
        assertEquals("UPDATE -3 1 -3 0 NEXT_ID 0 WIN_ID",message.serialize());

        message=rules.isValidMove(board,rules.getPlayer(0),new Move(3,-7,3,1));
        assertEquals("UPDATE 3 -7 3 1 NEXT_ID 1 WIN_ID",message.serialize());
    }
}
