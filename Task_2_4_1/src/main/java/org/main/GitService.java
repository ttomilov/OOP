package org.main;

import java.io.IOException;

class GitService {

    public static int cloneRepo(String url) {
        try {
            Process procCD = Runtime.getRuntime().exec("cd repos/");
            Process procCLONE = Runtime.getRuntime().exec("gir clone " + url);

            procCD.waitFor();

            if (procCD.exitValue() != 0) {
                return procCD.exitValue();
            }

            procCLONE.waitFor();

            if (procCLONE.exitValue() != 0) {
                return procCLONE.exitValue();
            }

            procCD.destroy();
            procCLONE.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int pullRepo(String repo) throws IOException, InterruptedException {
        int exitCode;
        if ((exitCode = cdForPull(repo)) != 0) {
            return exitCode;
        }
        Process procCLONE = Runtime.getRuntime().exec("gir pull");
        return 0;
    }

    private static int cdForClone() throws IOException, InterruptedException {
        Process procCD = Runtime.getRuntime().exec("cd repos/");
        procCD.waitFor();

        if (procCD.exitValue() != 0) {
            return procCD.exitValue();
        }

        procCD.destroy();
        return 0;
    }

    private static int cdForPull(String repo) throws IOException, InterruptedException {
        Process procCD = Runtime.getRuntime().exec("cd repos/" + repo);
        procCD.waitFor();

        if (procCD.exitValue() != 0) {
            return procCD.exitValue();
        }

        procCD.destroy();
        return 0;
    }
}
