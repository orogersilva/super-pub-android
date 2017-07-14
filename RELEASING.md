Release Process
===============

Release
-------

1. Open PR from `master` to `alpha` branch on Github of this project.

2. Pull PR result from remote `alpha` branch to local `alpha` branch.

3. Update the `CHANGELOG.md` file describing the internal and/or public changes.

4. Update the `whatsnew` file for describing news on app.

5. Commit the changes with `git commit -m "Preparing version X.Y.Z"`, replacing 'X.Y.Z' with the
    name of the new version.

6. Create a annotated tag with `git tag -a X.Y.Z -m "Version X.Y.Z"`, replacing 'X.Y.Z' with the
    name of the new version.

7. Push commits and tags (`git push origin alpha --follow-tags`).

8. :shipit:

9. After, merge `alpha` branch into `master` branch.