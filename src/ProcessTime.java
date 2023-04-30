import javax.swing.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcessTime {
    public static void main(String[] args) {
        String processName = args.length == 0 ? "mspaint.exe" : args[0];
        List<ProcessHandle> processes = ProcessHandle.allProcesses().collect(Collectors.toList());
        ProcessHandle ideaProcess = null;
        Instant start = Instant.now();
        Instant end;
        for (ProcessHandle process : processes) {
            Optional<String> command = process.info().command();
            if (command.isPresent() && command.get().contains(processName)) {
                ideaProcess = process;
                break;
            }

        }
        if (ideaProcess == null) {
            throw new IllegalArgumentException(processName + " process isn't alive");
        }else {
            System.out.println("start monitoring " + processName + " process time");
        }
        while (true) {
            if (!ideaProcess.isAlive()) {
                end = Instant.now();
                Duration duration = Duration.between(start, end);
                long hours = duration.toHours();
                duration = duration.minusHours(hours);
                long minutes = duration.toMinutes();
                duration = duration.minusMinutes(minutes);
                long seconds = duration.toSeconds();
                JOptionPane.showMessageDialog(null,
                        String.format("%s was launched %s:%s:%s",
                                processName,
                                hours,
                                minutes,
                                seconds));
                break;
            }
        }
    }

}
