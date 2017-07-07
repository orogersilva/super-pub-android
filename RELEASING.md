Release Process
===============

Release
-------

1. Update the `CHANGELOG.md` file describing the internal and/or public changes.

2. Commit the changes with `git commit -m "Preparing version X.Y.Z"`, replacing 'X.Y.Z' with the
    name of the new version.

3. Create a annotated tag with `git tag -a X.Y.Z -m "Version X.Y.Z"`, replacing 'X.Y.Z' with the
    name of the new version.

4. Push commits and tags (`git push --follow-tags`).

5. :shipit: