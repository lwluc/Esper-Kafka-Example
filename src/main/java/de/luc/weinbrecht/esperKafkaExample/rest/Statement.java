package de.luc.weinbrecht.esperKafkaExample.rest;

public class Statement {
    private String name;
    private String statement;

    public Statement() { }

    public Statement(String statement) {
        this.name = name;
        this.statement = statement;
    }

    public String getName() {
        return name;
    }

    public String getStatement() {
        return statement;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "name='" + name + '\'' +
                ", statement='" + statement + '\'' +
                '}';
    }
}
