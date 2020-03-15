package sample.Logic;


public class MassLogic implements IMassLogic{

    @Override
    public int getSoluteMass(int dryMass, int percent) {
        return dryMass * 100 / percent;
    }

    @Override
    public int getDryMass(int soluteMass, int percent) {
        return soluteMass * percent / 100;
    }
}
