pragma solidity ^0.4.24;

contract Elections{
    enum StateType { 
      candidateListed,
      votingBegins,
      votingEnds
    }

    StateType public State;
    address public candidateAddress;
    int public candidateVotes=0;
    string public candidateParty;
    address public Maintainer;
    address public Voter;
    string public CandidateName;


    constructor(address candidate,string party,string name) public {
        candidateAddress = candidate;
        candidateParty = party;
        State = StateType.candidateListed;
        CandidateName = name;
        Maintainer = msg.sender;
    }

    function vote() public {
        if(State!=StateType.votingBegins){
            revert();
        }else{
            candidateVotes += 1;
            Voter = msg.sender;
            State = StateType.votingBegins;
        }
    }

    function terminate() public {
        if(Maintainer != msg.sender){
            revert();
        }
        if(State != StateType.votingBegins){
            revert();
        }
        State = StateType.votingEnds;
    }

    function begin() public {
        if(Maintainer != msg.sender){
            revert();
        }
        if(State != StateType.candidateListed){
            revert();
        }
        State = StateType.votingBegins;
    }

}
