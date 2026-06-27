# `.cursor/` 目录树

`.cursor/` 工作区的完整目录结构。随 `.cursor/` 增删改（rules、skills、references/scripts/assets 等）同步更新；维护规则见 [rules/project-cursor-catalog-sync.mdc](rules/project-cursor-catalog-sync.mdc)，能力与编目说明见 [README.md](README.md)。

条目顺序与文件系统实际子项一致（按文件名字典序）。

```
.cursor/
├── README.md
├── directory-tree.md
├── audits/
│   └── skill-local-path-audit.md
├── rules/
│   ├── authoring-agent-skills.mdc
│   ├── chinese-simplified.mdc
│   ├── karpathy-guidelines.mdc
│   ├── project-cursor-catalog-sync.mdc
│   └── project-skill-conventions.mdc
└── skills/
    ├── android-dimen-dp-sp/SKILL.md
    ├── android-version-platform-adapt/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── android-xml-resource/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── code-method-normalize/SKILL.md
    ├── project-binding-adapter-from-source/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── project-devengine-implementation/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── project-gradle-central-deps/SKILL.md
    ├── project-gradle-third-party-version-upgrade/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── release-changelog-update/SKILL.md
    ├── ui-devsimple-viewtheme/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── ui-devwidget-round/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    ├── ui-dialogx-dialog/
    │   ├── SKILL.md
    │   └── references/
    │       └── reference.md
    └── ui-shadowlayout/
        ├── SKILL.md
        └── references/
            └── reference.md
```
