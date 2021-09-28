package com.example.kaurhw3;

public class ShuntingYard
{
    private String input;
    private Queue inputQ;
    private Queue outputQ;
    private Stack opStack;
    private Stack processStack;

    public ShuntingYard(String input)
    {
        this.input = input;
        this.inputQ = new Queue();
        this.outputQ = new Queue();
        this.opStack = new Stack();
        this.processStack = new Stack();

        this.FillInputQ();
        this.ProcessInputQ();
        this.ProcessOutputQ();

    }

    public String GetAnswer()
    {
        return this.processStack.peek().getPayload();
    }

    private Node DoMath(String op, Node Number1, Node Number2)
    {
        int N1 = Integer.parseInt(Number1.getPayload());
        int N2 = Integer.parseInt(Number2.getPayload());
        int answer;

        if(op.equals("+"))
        {
            answer = N2 + N1;
        }
        else if(op.equals("-"))
        {
            answer = N2 - N1;
        }
        else if(op.equals("*"))
        {
            answer = N2 * N1;
        }
        else
        {
            answer = N2 / N1;
        }
        return new Node("" + answer);
    }

    private void ProcessOutputQ()
    {
        while(!this.outputQ.isEmpty())
        {
            Node temp;
            temp = this.outputQ.removeFront();
            if(temp.isOperator())
            {
                Node answer = this.DoMath(temp.getPayload(), this.processStack.pop(), this.processStack.pop());
                this.processStack.push(answer);
            }
            else
            {
                this.processStack.push(temp);
            }
        }
    }

    private void AddToOpsStack(Node n)
    {
        while(!this.opStack.isEmpty() && n.getOpPower() <= this.opStack.peek().getOpPower())
            {
                System.out.println("Popping " + this.opStack.peek().getPayload());
                this.outputQ.addEnd(this.opStack.pop());
            }
                this.opStack.push(n);

    }

    private void ProcessInputQ()
    {
        Node temp;
        while(!this.inputQ.isEmpty())
        {
            temp = this.inputQ.removeFront();
            if(temp.isOperator())
            {
                this.AddToOpsStack(temp);
            }
            else
            {
                this.outputQ.addEnd(temp);
            }
        }
        while(!this.opStack.isEmpty())
        {
            this.outputQ.addEnd(this.opStack.pop());
        }
    }

    private void FillInputQ()
    {

        String currVal = "";
        for(int i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) == ' ')
            {
                inputQ.addEnd(currVal);
                currVal = "";
            }
            else
            {
                currVal = currVal + input.charAt(i);
            }
        }
        if(currVal.length() > 0)
        {
            inputQ.addEnd(currVal);
        }
    }

}
