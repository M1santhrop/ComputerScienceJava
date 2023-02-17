package chapter2;

import java.util.*;

public class Gene {
    private List<Codon> codons = new ArrayList<>();

    public Gene(String geneStr) {
        for (int i = 0; i < geneStr.length() - 3; i+= 3) {
            codons.add(new Codon(geneStr.substring(i, i + 3)));
        }
    }

    public boolean linearContains(Codon key) {
        for (Codon codon : codons) {
            if (codon.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean binaryContains(Codon key) {
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        Collections.sort(sortedCodons);
        int low = 0;
        int high = sortedCodons.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = codons.get(middle).compareTo(key);
            if (comparison > 0) {
                high = middle - 1;
            } else if (comparison < 0) {
                low = middle + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT";
        Gene gene = new Gene(geneStr);
        Codon acg = new Codon("ACG");
        Codon gat = new Codon("GAT");
        System.out.println(gene.linearContains(acg));
        System.out.println(gene.linearContains(gat));
        System.out.println(gene.binaryContains(acg));
        System.out.println(gene.binaryContains(gat));
    }

    public enum Nucleotide {
        A, C, G, T
    }

    public static class Codon implements Comparable<Codon> {
        public final Nucleotide first;
        public final Nucleotide second;
        public final Nucleotide third;
        private final Comparator<Codon> comparator = Comparator
                .comparing((Codon c) -> c.first)
                .thenComparing((Codon c) -> c.second)
                .thenComparing((Codon c) -> c.third);

        public Codon(String codonStr) {
            first = Nucleotide.valueOf(codonStr.substring(0, 1));
            second = Nucleotide.valueOf(codonStr.substring(1, 2));
            third = Nucleotide.valueOf(codonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon codon) {
            return comparator.compare(this, codon);
        }
    }
}
