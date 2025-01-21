package com.jkpr.chinesecheckers;

import com.jkpr.chinesecheckers.server.gamelogic.*;
import com.jkpr.chinesecheckers.server.gamelogic.boards.AbstractBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.CCBoard;
import com.jkpr.chinesecheckers.server.gamelogic.boards.Position;
import com.jkpr.chinesecheckers.server.gamelogic.rules.AbstractRules;
import com.jkpr.chinesecheckers.server.gamelogic.rules.CCRules;
import com.jkpr.chinesecheckers.server.message.MoveMessage;
import com.jkpr.chinesecheckers.server.message.UpdateMessage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameTest extends TestCase {
    public static Test suite()
    {
        return new TestSuite( GameTest.class );
    }
    public void testMovesCycle(){
        Game game=new Game();
        AbstractRules rules=new CCRules(3);
        AbstractBoard board=new CCBoard();
        game.setBoard(board);
        game.setRules(rules);

        game.generate();

        UpdateMessage updateMessage=game.nextMove(new MoveMessage(1,-5,1,-4),rules.getPlayer(0));
        assertEquals("1 -5 1 -4 NEXT_ID 1 WIN_ID",updateMessage.getContent());

        updateMessage=game.nextMove(new MoveMessage(4,1,3,1),rules.getPlayer(1));
        assertEquals("4 1 3 1 NEXT_ID 2 WIN_ID",updateMessage.getContent());

        updateMessage=game.nextMove(new MoveMessage(-5,4,-4,4),rules.getPlayer(2));
        assertEquals("-5 4 -4 4 NEXT_ID 0 WIN_ID",updateMessage.getContent());
    }
    public void testWin(){
        Game game=new Game();
        AbstractRules rules=new CCRules(3);
        AbstractBoard board=new CCBoard();
        game.setBoard(board);
        game.setRules(rules);

        game.generate();

        for(Position pos:board.getCells().keySet())
        {
            if(pos.getY()<-4)
                board.makeMove(new Move(pos,new Position(-1*pos.getX(),-1*pos.getY())));
        }
        board.makeMove(new Move(-1,5,-1,4));

        UpdateMessage updateMessage=game.nextMove(new MoveMessage(-1,4,-1,5),rules.getPlayer(0));

        assertEquals("-1 4 -1 5 NEXT_ID 1 WIN_ID 0 END",updateMessage.getContent());
    }
    public void testEnd(){
        Game game=new Game();
        AbstractRules rules=new CCRules(3);
        AbstractBoard board=new CCBoard();
        game.setBoard(board);
        game.setRules(rules);

        game.generate();

        for(Position pos:board.getCells().keySet())
        {
            if(pos.getY()<-4 || pos.getX()<-4 || pos.getX()+pos.getY()>4)
                board.makeMove(new Move(pos,new Position(-1*pos.getX(),-1*pos.getY())));
        }
        board.makeMove(new Move(-1,5,-1,4));
        board.makeMove(new Move(-1,-4,0,-4));
        board.makeMove(new Move(5,-4,4,-4));



        UpdateMessage updateMessage=game.nextMove(new MoveMessage(-1,4,-1,5),rules.getPlayer(0));
        assertEquals("-1 4 -1 5 NEXT_ID 1 WIN_ID 0 END",updateMessage.getContent());
    }
    public void testSkip(){
        Game game=new Game();
        AbstractRules rules=new CCRules(3);
        AbstractBoard board=new CCBoard();
        game.setBoard(board);
        game.setRules(rules);

        game.generate();

        UpdateMessage updateMessage=game.nextMove(new MoveMessage(),rules.getPlayer(0));
        assertEquals("SKIP NEXT_ID 1",updateMessage.getContent());
        updateMessage=game.nextMove(new MoveMessage(),rules.getPlayer(1));
        assertEquals("SKIP NEXT_ID 2",updateMessage.getContent());
        updateMessage=game.nextMove(new MoveMessage(),rules.getPlayer(2));
        assertEquals("SKIP NEXT_ID 0",updateMessage.getContent());
        updateMessage=game.nextMove(new MoveMessage(),rules.getPlayer(0));
        assertEquals("SKIP NEXT_ID 1",updateMessage.getContent());
    }
}
