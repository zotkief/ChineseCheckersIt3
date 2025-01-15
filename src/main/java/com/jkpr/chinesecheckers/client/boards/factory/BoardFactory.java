package com.jkpr.chinesecheckers.client.boards.factory;

import com.jkpr.chinesecheckers.client.boards.AbstractBoardClient;

public interface BoardFactory {
    public AbstractBoardClient generate(int id,int count);
}
