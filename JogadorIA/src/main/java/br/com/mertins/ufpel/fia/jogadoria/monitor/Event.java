package br.com.mertins.ufpel.fia.jogadoria.monitor;

import br.com.mertins.ufpel.fia.jogadoria.util.Move;
import java.time.Instant;
import java.util.Objects;

/**
 *
 * @author mertins
 */
public class Event {

    private final String id;
    private final Move node;
    private final int processors;
    private final long totalMemory;
    private final long freeMemory;
    private final long maxMemory;
    private final Instant instant;

    public Event(String identificador) {
        this(identificador, null);
    }

    public Event(String identificador, Move node) {
        this.id = identificador;
        this.node = node;
        processors = Runtime.getRuntime().availableProcessors();
        totalMemory = Runtime.getRuntime().totalMemory() / 1048576;
        freeMemory = Runtime.getRuntime().freeMemory() / 1048576;
        maxMemory = Runtime.getRuntime().maxMemory() / 1048576;
        this.instant = Instant.now();

    }

    public String getId() {
        return id;
    }

    public int getProcessors() {
        return processors;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public Instant getInstant() {
        return instant;
    }

    public Move getNode() {
        return node;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Id[%s]  Proces[%d]   Mem disp[%dM]  Livre[%dM]   Máx[%dM] ",
                id, processors, totalMemory, freeMemory, maxMemory);
    }

}
