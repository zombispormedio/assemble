package com.zombispormedio.assemble.views.fragments;

import com.zombispormedio.assemble.models.Team;
import com.zombispormedio.assemble.views.fragments.IFragmentView;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 03/09/2016.
 */
public interface ITeamsView extends IFragmentView {

     void bindTeams(ArrayList<Team> data );

     void updateTeam(int position, Team team);

     void finishRefresh();
}
