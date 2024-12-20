import java.util.ArrayList;
import java.util.List;

public class JavaDay09 {

    public Long part2(List<Integer> input) {
        System.out.println(input);

        List<File> files = new ArrayList<>();
        for (int id = input.getLast(); id >= 0; id--) {
            int lp = 0;
            int rp = input.size()-1;
            while (input.get(lp) != id || input.get(rp) != id) {
                if(input.get(lp) != id) {
                    lp++;
                }
                if(input.get(rp) != id) {
                    rp--;
                }
            }

            files.add(new File(id, lp, rp-lp+1));
        }
        System.out.println(files);

        for (File file : files) {
            int freeSizeToFind = file.size;
            int lp = 0;
            int rp = 0;
            while (lp < file.startIndex && rp < file.startIndex) {
                if (input.get(lp) != -1) {
                    lp++;
                } else {
                    rp++;
                    if (rp == file.startIndex) {
                        break;
                    }
                    if (input.get(rp) == -1 && rp-lp+1 == freeSizeToFind) {
                        break;
                    } else if(lp < rp && input.get(rp) != -1) {
                        lp = rp;
                    }
                }
            }
            if (rp-lp+1 == freeSizeToFind && input.get(lp) == -1 && input.get(rp) == -1) {
                System.out.println("Found place for file " + file.id + " at " + lp + "with size " + freeSizeToFind);
                moveFileInMemory(input, file, lp);
            } else {
                System.out.println("No place for file " + file.id);
            }
            System.out.println(input);
        }

        long checkSum = 0;
        long currentIndex = 0;
        for (Integer integer : input) {
            checkSum += integer == -1 ? 0 : integer * currentIndex;
            currentIndex++;
        }
        return checkSum;
    }

    private void moveFileInMemory(List<Integer> input, File file, int lp) {
        for (int i = 0; i < file.size; i++) {
            input.set(lp+i, file.id);
            input.set(file.startIndex+i, -1);
        }
    }

    private record File(int id, int startIndex, int size) {}


}
