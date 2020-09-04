package cse2010.homework1;

public class ArraySparseMatrix implements SparseMatrix {

    public static final int DEFAULT_CAPACITY = 1024;

    private int rowCount;
    private int columnCount;
    private int elemCount;
    private Entry[] elements = new Entry[0];

    public ArraySparseMatrix(int rowCount, int columnCount, int capacity) {
        elements = new Entry[capacity];
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.elemCount = 0;
    }

    public ArraySparseMatrix(int rowCount, int columnCount) {
        this(rowCount, columnCount, DEFAULT_CAPACITY);
    }

    /*
     * This routine simulates reading from files using two-dimensional matrix.
     */
    public static SparseMatrix create(double[][] aMatrix, int rowCount, int columnCount, int elemCount) {
        ArraySparseMatrix matrix = new ArraySparseMatrix(rowCount, columnCount, elemCount);

        int nonZeroCount = 0;
        for (int i = 0; i < aMatrix.length; i++)
            for (int j = 0; j < aMatrix[i].length; j++) {
                if (Double.compare(aMatrix[i][j], 0.0) != 0) {
                    matrix.put(new Entry(i, j, aMatrix[i][j]));
                    nonZeroCount++;
                }
            }
      
        if(nonZeroCount != elemCount) {
        	throw new IllegalStateException("Non zero count doesn't match");
        }
        return matrix;
    }

    private void put(Entry entry) {
        elements[elemCount++] = entry;
    }


    @Override
    public SparseMatrix transpose() {
    	
    	ArraySparseMatrix result = new ArraySparseMatrix(this.getColumnCount(), this.getRowCount());
    	for(int i=0; i<this.getElemCount(); i++) {
    		result.put(new Entry(this.getElements()[i].getCol(),this.getElements()[i].getRow(),this.getElements()[i].getValue()));
    	}
    	
    	int next, nextCol, cur, curCol;
    	for(int i=0;i<result.getElemCount(); i++) {
				
			cur = result.getElements()[i].getRow();
			curCol = result.getElements()[i].getCol();
			if(i!= result.getElemCount()-1) {
				next = result.getElements()[i+1].getRow();
				nextCol = result.getElements()[i+1].getCol();
			} else {
				break;
			}
			if(cur>next) {
				Entry temp = result.getElements()[i];
				result.getElements()[i] = result.getElements()[i+1];
				result.getElements()[i+1] = temp;
			} else if (cur==next) {
				if(curCol>nextCol) {
					Entry temp = result.getElements()[i];
					result.getElements()[i] = result.getElements()[i+1];
					result.getElements()[i+1] = temp;
				}
			}
		}
    	return result;

    }

    @Override
    public SparseMatrix add(SparseMatrix other) {
        if (this.rowCount != other.getRowCount() || this.columnCount != other.getColumnCount())
            throw new IllegalArgumentException("Matrix size doesn't match");

        ArraySparseMatrix castASM = (ArraySparseMatrix) other;
        ArraySparseMatrix result = new ArraySparseMatrix(this.getRowCount(), this.getColumnCount());
        
       
        int i=0;
        int j=0;
        while(i<this.getElemCount() && j<castASM.getElemCount()) {
        	
            Entry entryThis = this.getElements()[i];
            Entry entryOther = castASM.getElements()[j];
            int rowThis = entryThis.getRow();
            int colThis = entryThis.getCol();
            int rowOther = entryOther.getRow();
            int colOther = entryOther.getCol();
            
        	if(rowThis > rowOther) {
        		result.put(entryOther);
        		j++;
        	} else if (rowThis < rowOther) {
        		result.put(entryThis);
        		i++;
        	} else {
        		if (colThis > colOther) {
        			result.put(entryOther);
        			j++;
        		} else if (colThis < colOther) {
        			result.put(entryThis);
        			i++;
        		} else {
        			double sum = entryThis.getValue() + entryOther.getValue();
        			result.put(new Entry(rowThis, colThis, sum));
        			i++;
        			j++;
        		}
        	}
        	if(i==this.getElemCount()&&j<castASM.getElemCount()) {
        		while(j<castASM.getElemCount()) {
        			result.put(entryOther);
        			j++;
        		}
        	} else if (j==castASM.getElemCount()&&i<this.getElemCount()) {
        		while(i<this.getElemCount()) {
        			result.put(entryThis);
        			i++;
        		}
        		
        	}
        	
        }
        
        return result;

    }

    public Entry[] getElements() {
        return elements;
    }

    @Override
    public SparseMatrix multiply(SparseMatrix aMatrix) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getElemCount() {
        return elemCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ArraySparseMatrix)) return false;
        ArraySparseMatrix other = (ArraySparseMatrix) obj;

        if (rowCount != other.rowCount || columnCount != other.columnCount || elemCount != other.elemCount)
            return false;

        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(other.elements[i])) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rowCount + "\t" + columnCount + "\t" + elemCount + "\n");
        for (int i = 0; i < elemCount; i ++)
            builder.append(elements[i] + "\n");

        return builder.toString();
    }
}
